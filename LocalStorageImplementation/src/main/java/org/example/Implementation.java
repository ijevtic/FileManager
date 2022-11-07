package org.example;

import org.raf.exceptions.BrokenConfigurationException;
import org.raf.exceptions.IllegalDestinationException;
import org.raf.specification.*;
import org.raf.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.List;

import static org.example.Utils.ConfigurationCheck.*;
import static org.example.Constants.*;
import static org.example.Utils.StringUtils.getParentPath;

public class Implementation extends FileManager{
    @Override
    public void createStorage()throws RuntimeException {
        createStorage(CONFIGURATION, STORAGE_PATH, STORAGE_NAME);
    }

    @Override
    public void createStorage(String path, String name)throws RuntimeException {
        createStorage(CONFIGURATION, path, name);
    }

    @Override
    public void createStorage(String name) throws RuntimeException {
        createStorage(CONFIGURATION, STORAGE_PATH, name);
    }

    @Override
    public void createStorage(Configuration configuration) throws RuntimeException{
        createStorage(configuration, STORAGE_PATH, STORAGE_NAME);
    }

    @Override
    public void createStorage(Configuration configuration, String name) throws RuntimeException{
        createStorage(configuration, STORAGE_PATH, name);
    }

    @Override
    public void createStorage(Configuration configuration, String rootPath, String name) throws RuntimeException{
        String storagePath = formatPath(rootPath, name);
        createDir(storagePath);

        setStorage(new Storage(storagePath, configuration));
        try {
            getStorage().updateConfiguration();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadStorage(String path) throws IOException, BrokenConfigurationException {
        Storage s = new Storage(path);
        s.readConfiguration();
        setStorage(s);
        System.out.println(getStorage());
    }

    @Override
    public void createDirectory(String rootPath) throws BrokenConfigurationException {
        createDirectory(rootPath, -1);
    }

    @Override
    public void createDirectory(String rootPath, int maxFileCount) throws BrokenConfigurationException {
        if(!fileCountCheck(getStorage(), rootPath, 1)) {
            throw new BrokenConfigurationException("Broken exception for file " + rootPath);
        }
        createAndStoreDirectory(formatPath(rootPath, "dir"), maxFileCount);
    }

    @Override
    public void createDirectory(String rootPath, String pattern) throws Exception{
        createDirectory(rootPath, pattern, -1);
    }

    @Override
    public void createDirectory(String rootPath, String pattern, int maxFileCount) throws Exception {
        List<String> dirNames = Utils.dirNamesFromPattern(pattern);
        if(!fileCountCheck(getStorage(), rootPath, dirNames.size())) {
            throw new BrokenConfigurationException("Broken exception for file " + rootPath);
        }
        for(String name: dirNames) {
            createAndStoreDirectory(formatPath(rootPath, name), maxFileCount);
        }
    }

    private void createAndStoreDirectory(String path, int maxFileCount) throws RuntimeException {
        if(!createDir(path))
            return;
        if(maxFileCount < 0)
            return;
        getStorage().getConfiguration().addCountForDir(path, maxFileCount);
        try {
            getStorage().updateConfiguration();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addFiles(List<SpecFile> fileList, String destinationPath) throws Exception{
        if(!fileCountCheck(getStorage(), destinationPath, fileList.size())
        || !extensionCheck(getStorage(), fileList)
        || !fileSizeCheck(getStorage(), fileList)) {
            throw new BrokenConfigurationException("Broken exception for file " + destinationPath);
        }
        for(SpecFile f : fileList) {
            if(!isAncestor(getStorage().getPath(), destinationPath) || isAncestor(getStorage().getPath(), f.getPath())) {
                throw new IllegalDestinationException("Illegal destination " + f.getPath() + " " + destinationPath);
            }
            try {
                copy(f, new SpecFile(destinationPath));
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
    public void moveFile(SpecFile file, SpecFile destination) throws BrokenConfigurationException, IllegalDestinationException {
        if(!isAncestor(getStorage().getPath(), destination.getPath())) {
            throw new IllegalDestinationException("Illegal destination " + file.getPath() + " " + destination.getPath());
        }
        if(!fileCountCheck(getStorage(), destination.getPath(), 1)) {
            throw new BrokenConfigurationException("Broken exception for file " + destination.getPath());
        }
        try {
            String destinationPath = formatPath(destination.getPath(), file.getFileName());
            Files.move(Paths.get(file.getPath()), Paths.get(destinationPath));
            getStorage().getConfiguration().moveCountForDir(file.getPath(), destinationPath);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void moveFile(SpecFile file, String destination) throws Exception {
        moveFile(file, new SpecFile(destination));
    }

    @Override
    public void moveFile(String path, String destinationPath) throws Exception{
        moveFile(new SpecFile(path), new SpecFile(destinationPath));
    }

    @Override
    public void moveFiles(List<String> list, String destinationPath) throws Exception {
        if(!fileCountCheck(getStorage(), destinationPath, list.size())) {
            throw new BrokenConfigurationException("Broken exception for file " + destinationPath);
        }
        for(String path: list) {
            moveFile(new SpecFile(path), new SpecFile(destinationPath));
        }
    }

    @Override
    public void download(SpecFile source, SpecFile destination) throws Exception{
        if(isAncestor(getStorage().getPath(), destination.getPath())) {
            throw new IllegalDestinationException("Illegal destination " + source.getPath() + " " + destination.getPath());
        }
        try {
            copy(source, destination);
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
    public List<SpecFile> returnDirectoryFiles(String s) {
        return null;
    }

    @Override
    public List<SpecFile> returnDirectoryFiles(String s, String s1) {
        return null;
    }

    @Override
    public List<SpecFile> returnDirectoryFiles(String s, List<String> list) {
        return null;
    }

    @Override
    public List<SpecFile> returnDirectoryFiles(String s, List<String> list, String s1) {
        return null;
    }

    @Override
    public List<SpecFile> returnSubdirectoryFile(String s) {
        return null;
    }

    @Override
    public List<SpecFile> returnSubdirectoryFile(String s, String s1) {
        return null;
    }

    @Override
    public List<SpecFile> returnSubdirectoryFile(String s, List<String> list) {
        return null;
    }

    @Override
    public List<SpecFile> returnSubdirectoryFile(String s, List<String> list, String s1) {
        return null;
    }

    @Override
    public List<SpecFile> returnAllDirectoryFiles(String s) {
        return null;
    }

    @Override
    public List<SpecFile> returnAllDirectoryFiles(String s, String s1) {
        return null;
    }

    @Override
    public List<SpecFile> returnAllDirectoryFiles(String s, List<String> list) {
        return null;
    }

    @Override
    public List<SpecFile> returnAllDirectoryFiles(String s, List<String> list, String s1) {
        return null;
    }

    @Override
    public boolean containsFile(String s, List<String> list) {
        return false;
    }

    @Override
    public String returnFileLocation(String s) {
        return null;
    }

    @Override
    public void sortFiles(SortingCriteria sortingCriteria) {

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

    private boolean createDir(String path) throws RuntimeException{
        if(Files.exists(Paths.get(path)))
            return false;
        try {
            new File(path).mkdirs();
            System.out.printf("Directory on path %s is created!%n", path);
            return true;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void copy(SpecFile source, SpecFile destination) throws IOException {
        String destinationPath = formatPath(destination.getPath(), source.getFileName());
        Files.copy(Paths.get(source.getPath()), Paths.get(destinationPath));
    }

    private String formatPath(String prefixPath, String fileName) {
        String path = prefixPath;
        if(path.charAt(path.length()-1) != '/')
            path += '/';
        path += fileName;
        return path;
    }
}
