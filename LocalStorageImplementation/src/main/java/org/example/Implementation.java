package org.example;

import org.raf.exceptions.BrokenConfigurationException;
import org.raf.specification.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.raf.utils.Utils.*;

public class Implementation extends FileManager{

    @Override
    public void createStorage(Configuration configuration, String rootPath, String name) throws RuntimeException{
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
        for(String str : fileNames){
            String path = formatPath(s,str);
            BasicFileAttributes fileAttributes = Files.readAttributes(Path.of(path), BasicFileAttributes.class);
            specFiles.add(new SpecFile(path, str, fileAttributes.creationTime(), fileAttributes.lastModifiedTime(), fileAttributes.isDirectory()));
        }

        return specFiles;
    }
    @Override
    public List<SpecFile> returnDirectoryFiles(String s, String s1) throws Exception{
        return returnFilesWithSubstring(returnDirectoryFiles(s), s1);
    }

    @Override
    public List<SpecFile> returnDirectoryFiles(String s, List<String> extensions) throws Exception {
        List<SpecFile> specFiles = returnDirectoryFiles(s);
        return filesWithRightExtension(specFiles,extensions);
    }

    @Override
    public List<SpecFile> returnDirectoryFiles(String s, List<String> extensions, String s1) throws Exception {
        return filesWithRightExtension(returnFilesWithSubstring(returnDirectoryFiles(s), s1), extensions);
    }

    @Override
    public List<SpecFile> returnSubdirectoryFile(String s) throws Exception {
        List<SpecFile> directoryFiles = returnDirectoryFiles(s);
        List<SpecFile> subdirectoryFiles = new ArrayList<>();
        for(SpecFile sf : directoryFiles){
            subdirectoryFiles.addAll(returnDirectoryFiles(sf.getPath()));
        }
        return subdirectoryFiles;
    }

    @Override
    public List<SpecFile> returnSubdirectoryFile(String s, String substring) throws Exception {
        return returnFilesWithSubstring(returnSubdirectoryFile(s), substring);
    }

    @Override
    public List<SpecFile> returnSubdirectoryFile(String s, List<String> extensions) throws Exception {
        return filesWithRightExtension(returnSubdirectoryFile(s), extensions);
    }

    @Override
    public List<SpecFile> returnSubdirectoryFile(String s, List<String> extensions, String substring) throws Exception {
        return filesWithRightExtension(returnFilesWithSubstring(returnSubdirectoryFile(s), substring), extensions);

    }

    @Override
    public List<SpecFile> returnAllDirectoryFiles(String s) throws Exception {
        List<SpecFile> specFiles = returnDirectoryFiles(s);
        for(SpecFile sf : specFiles){
            specFiles.addAll(returnDirectoryFiles(sf.getPath()));
        }
        return specFiles;
    }

    @Override
    public List<SpecFile> returnAllDirectoryFiles(String path, String substring) throws Exception {
        return returnFilesWithSubstring(returnDirectoryFiles(path), substring);
    }

    @Override
    public List<SpecFile> returnAllDirectoryFiles(String s, List<String> extensions) throws Exception {
        return filesWithRightExtension(returnAllDirectoryFiles(s), extensions);
    }

    @Override
    public List<SpecFile> returnAllDirectoryFiles(String s, List<String> extensions, String substring) throws Exception {
        return filesWithRightExtension(returnFilesWithSubstring(returnAllDirectoryFiles(s), substring), extensions);
    }

    @Override
    public boolean containsFile(String dirPath, List<String> fileNames) {
        File f = new File(dirPath);
        String [] fileList = f.list();
        if(fileList == null)
            throw new RuntimeException();
        List<String> fileNamesInDir = List.of((fileList));
        for(String fileName : fileNames){
            if(!fileNamesInDir.contains(fileName)) return false;
        }
        return true;
    }

    @Override
    public String returnFileLocation(String s) {
        String path = getStorage().getPath();
        return dfs(path,s);
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

    @Override
    public List<String> returnFileName(List<SpecFile> list) {
        List<String> fileNames = new ArrayList<>();
        for(SpecFile sf: list) fileNames.add(sf.getFileName());
        return fileNames;
    }

    @Override
    public List<String> returnFilePath(List<SpecFile> list) {
        List<String> filePaths = new ArrayList<>();
        for(SpecFile sf: list) filePaths.add(sf.getPath());
        return filePaths;
    }

    @Override
    public List<FileTime> returnDateCreated(List<SpecFile> list) {
        List<FileTime> creationDates = new ArrayList<>();
        for(SpecFile sf: list) creationDates.add(sf.getDateCreated());
        return creationDates;
    }

    @Override
    public List<FileTime> returnDateMModified(List<SpecFile> list) {
        List<FileTime> modificationDates = new ArrayList<>();
        for(SpecFile sf: list) modificationDates.add(sf.getDateModified());
        return modificationDates;

    }

    @Override
    public List<Boolean> returnIfDepository(List<SpecFile> list) {
        List<Boolean> booleans = new ArrayList<>();
        for(SpecFile sf: list) booleans.add(sf.isDirectory());
        return booleans;

    }


    private String dfs(String path, String name){
        File f = new File(path);
        String [] fileList = f.list();
        if(fileList == null)
            throw new RuntimeException();
        List<String> fileNames = List.of(fileList);
        if (fileNames.contains(name)) return formatPath(path,name);
        else {
            for(String name2 : fileNames){
                String path2 = formatPath(path, name2);
                dfs(path2, name);
            }
        }
        return null;
    }

    private String formatPath(String prefixPath, String fileName) {
        String path = prefixPath;
        if(path.charAt(path.length()-1) != '/')
            path += '/';
        path += fileName;
        return path;
    }
    public List<SpecFile> returnFilesWithSubstring(List<SpecFile> specFiles, String substring){
        List<SpecFile> returnList = new ArrayList<>();
        for(SpecFile sf : specFiles){
            String fileName = sf.getFileName();
            if(fileName.contains(substring)){returnList.add(sf);}
        }
        return returnList;
    }

    public List<SpecFile> filesWithRightExtension (List<SpecFile> specFiles, List<String> extensions){
        List<SpecFile> returnList = new ArrayList<>();
        for (SpecFile sf : specFiles){
            String extension = getExtension(sf.getFileName());
            if (extensions.contains(extension)){ returnList.add(sf);}
        }
        return returnList;
    }

}
