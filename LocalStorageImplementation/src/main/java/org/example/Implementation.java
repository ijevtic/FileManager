package org.example;

import org.raf.exceptions.BrokenConfigurationException;
import org.raf.exceptions.FileNotFoundCustomException;
import org.raf.specification.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;


public class Implementation extends FileManager{

    @Override
    public void createStorage(Configuration configuration, String rootPath, String name) throws RuntimeException, FileNotFoundCustomException {
        String storagePath = formatPath(rootPath, name);

        setStorage(new StorageImpl(storagePath, configuration, new FileHandlerImplementation()));
        getStorage().getFileHandler().createDirectory(storagePath);
        try {
            getStorage().updateConfiguration();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadStorage(String path) throws IOException, BrokenConfigurationException {
        Storage s = new StorageImpl(path);
        s.setFileHandler(new FileHandlerImplementation());
        s.readConfiguration();
        setStorage(s);
        System.out.println(getStorage());
    }

    @Override
    public List<SpecFile> returnDirectoryFiles(String s)throws Exception {
        File f = new File(s);
        String[] fileNames = f.list();
        List<SpecFile> specFiles = new ArrayList<>();
        if(fileNames != null){
            for(String str : fileNames){
                String path = formatPath(s,str);
                BasicFileAttributes fileAttributes = Files.readAttributes(Path.of(path), BasicFileAttributes.class);
                specFiles.add(new SpecFile(path, str, fileAttributes.creationTime(), fileAttributes.lastModifiedTime(), fileAttributes.isDirectory()));
            }
        }
//        if(fileNames == null)
//            throw new RuntimeException();
//        for(String str : fileNames){
//            String path = formatPath(s,str);
//            BasicFileAttributes fileAttributes = Files.readAttributes(Path.of(path), BasicFileAttributes.class);
//            specFiles.add(new SpecFile(path, str, fileAttributes.creationTime(), fileAttributes.lastModifiedTime(), fileAttributes.isDirectory()));
//        }

        return specFiles;
    }



    @Override
    public List<SpecFile> sortFiles(SortingCriteria sortingCriteria, List<SpecFile> files) {
        ESortingOrder order = sortingCriteria.getSortingOrder();
        if (order.equals(ESortingOrder.DESCENDING)){
            files.sort(new SpecFIleComparator(sortingCriteria).reversed());
        }
        else{
            files.sort(new SpecFIleComparator(sortingCriteria));
        }
        return files;
    }

    @Override
    public List<SpecFile> returnFilesModifiedDuringPeriod(FileTime startDate, FileTime endDate, String path) throws Exception {
        List<SpecFile> files = returnDirectoryFiles(path);
        List<SpecFile> returnList = new ArrayList<>();
        for(SpecFile sf : files){
            if(inPeriod(sf.getDateModified(), startDate, endDate)){
                returnList.add(sf);
            }
        }
        return returnList;
    }

    @Override
    public List<SpecFile> returnFilesCreatedDuringPeriod(FileTime startDate, FileTime endDate, String path) throws Exception {
        List<SpecFile> files = returnDirectoryFiles(path);
        List<SpecFile> returnList = new ArrayList<>();
        for(SpecFile sf : files){
            if(inPeriod(sf.getDateCreated(), startDate, endDate)){
                returnList.add(sf);
            }
        }
        return returnList;
    }

    private boolean inPeriod(FileTime a, FileTime b1, FileTime b2){
        return a.compareTo(b1) >= 0 && a.compareTo(b2) <= 0;
    }

    private String formatPath(String prefixPath, String fileName) {
        String path = prefixPath;
        if(path.charAt(path.length()-1) != '/')
            path += '/';
        path += fileName;
        return path;
    }


}
