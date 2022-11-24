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

import static org.example.Utils.convertToLocalDateTime;


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
                specFiles.add(new SpecFile(path, str, convertToLocalDateTime(fileAttributes.creationTime()) , convertToLocalDateTime(fileAttributes.lastModifiedTime()), fileAttributes.isDirectory()));
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



    private String formatPath(String prefixPath, String fileName) {
        String path = prefixPath;
        if(path.charAt(path.length()-1) != '/')
            path += '/';
        path += fileName;
        return path;
    }


}
