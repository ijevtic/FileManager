package org.example;

import org.raf.exceptions.BrokenConfigurationException;
import org.raf.exceptions.IllegalDestinationException;
import org.raf.specification.*;
import org.raf.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
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
    public void addFiles(List<SpecFile> fileList, String destinationPath) throws Exception{
        if(!getStorage().fileCountCheck(destinationPath, fileList.size())
        || !getStorage().extensionCheck(fileList)
        || !getStorage().fileSizeCheck(fileList)) {
            throw new BrokenConfigurationException("Broken exception for file " + destinationPath);
        }
        for(SpecFile f : fileList) {
            if(!isAncestor(getStorage().getPath(), destinationPath) || isAncestor(getStorage().getPath(), f.getPath())) {
                throw new IllegalDestinationException("Illegal destination " + f.getPath() + " " + destinationPath);
            }
            try {
                getStorage().getFileHandler().copy(f, new SpecFile(destinationPath));
            } catch (IOException e) {
                throw new Exception(e);
            }
        }
    }

    @Override
    public void removeFile(String filePath) throws Exception{
        removeFile(new SpecFile(filePath));
    }

    @Override
    public void removeFile(SpecFile file) throws Exception{
        try {
            Files.delete(Paths.get(file.getPath()));
            getStorage().getConfiguration().removeCountForDir(file.getPath());
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    @Override
    public void removeFiles(String[] list) throws Exception{
        for(String path: list) {
            removeFile(new SpecFile(path));
        }
    }

    @Override
    public void removeFiles(SpecFile[] list) throws Exception{
        for(SpecFile file: list) {
            removeFile(file);
        }
    }

    @Override
    public void download(SpecFile source, SpecFile destination) throws Exception{
        if(isAncestor(getStorage().getPath(), destination.getPath())) {
            throw new IllegalDestinationException("Illegal destination " + source.getPath() + " " + destination.getPath());
        }
        try {
            getStorage().getFileHandler().copy(source, destination);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    @Override
    public void download(SpecFile source, String destinationPath) throws Exception{
        download(source, new SpecFile(destinationPath));
    }

    @Override
    public void download(String sourcePath, String destinationPath) throws Exception{
        download(new SpecFile(sourcePath), new SpecFile(destinationPath));
    }

    @Override
    public boolean rename(SpecFile file, String newName) {
        File oldFile = new File(file.getPath());
        File newFile = new File(getParentPath(file.getPath())+newName);
        getStorage().getConfiguration().moveCountForDir(oldFile.getPath(), newFile.getPath());
        return oldFile.renameTo(newFile);
    }

    @Override
    public boolean rename(String sourcePath, String newName) {
        return rename(new SpecFile(sourcePath), newName);
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
        List<String> fileNamesInDir = List.of(f.list());
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
    public void sortFiles(SortingCriteria sortingCriteria, List<SpecFile> files) {
        //ESortingOrder order = sortingCriteria.getSortingOrder();

    }

    @Override
    public List<SpecFile> returnModifiedFiles(String s, String s1, String s2) {
        return null;
    }

    @Override
    public List<String> returnFileName(List<SpecFile> list) {
        return null;
    }

    @Override
    public List<String> returnFilePath(List<SpecFile> list) {
        return null;
    }

    @Override
    public List<FileTime> returnDateCreated(List<SpecFile> list) {
        return null;
    }

    @Override
    public List<FileTime> returnDateMModified(List<SpecFile> list) {
        return null;
    }

    @Override
    public List<Boolean> returnIfDepository(List<SpecFile> list) {
        return null;
    }

    private  List<SpecFile> listSort(SortingCriteria sortingCriteria, List<SpecFile> files){
        Comparator<SpecFile> compareByName;
        return files;
    }

    private String dfs(String path, String name){
        File f = new File(path);
        List<String> fileNames = List.of(f.list());
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
