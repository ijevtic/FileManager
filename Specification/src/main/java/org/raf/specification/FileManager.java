package org.raf.specification;

import org.raf.exceptions.BrokenConfigurationException;
import org.raf.exceptions.FileNotFoundCustomException;
import org.raf.exceptions.IllegalDestinationException;
import org.raf.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;

import static org.raf.utils.Constants.*;
import static org.raf.utils.Utils.*;

public abstract class FileManager implements IFileManager{

    private Storage storage = null;

    @Override
    public void createStorage() throws Exception {
        createStorage(CONFIGURATION, STORAGE_PATH, STORAGE_NAME);
    }

    @Override
    public void createStorage(String path, String name) throws Exception {
        createStorage(CONFIGURATION, path, name);
    }

    @Override
    public void createStorage(String name) throws Exception {
        createStorage(CONFIGURATION, STORAGE_PATH, name);
    }

    @Override
    public void createStorage(Configuration configuration) throws Exception {
        createStorage(configuration, STORAGE_PATH, STORAGE_NAME);
    }

    @Override
    public void createStorage(Configuration configuration, String name) throws Exception {
        createStorage(configuration, STORAGE_PATH, name);
    }

    @Override
    public void createDirectory(String rootPath) throws BrokenConfigurationException, FileNotFoundCustomException {
        createDirectory(rootPath, -1);
    }

    @Override
    public void createDirectory(String rootPath, int maxFileCount) throws BrokenConfigurationException, FileNotFoundCustomException {
        if(!getStorage().fileCountCheck(rootPath, 1)) {
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
        if(!getStorage().fileCountCheck(rootPath, dirNames.size())) {
            throw new BrokenConfigurationException("Broken exception for file " + rootPath);
        }
        for(String name: dirNames) {
            createAndStoreDirectory(formatPath(rootPath, name), maxFileCount);
        }
    }

    private void createAndStoreDirectory(String path, int maxFileCount) throws RuntimeException, FileNotFoundCustomException {
        if(!getStorage().getFileHandler().createDirectory(path))
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
    public void moveFile(SpecFile file, SpecFile destination) throws BrokenConfigurationException, IllegalDestinationException {
        if(!isAncestor(getStorage().getPath(), destination.getPath())) {
            throw new IllegalDestinationException("Illegal destination " + file.getPath() + " " + destination.getPath());
        }
        if(!getStorage().fileCountCheck(destination.getPath(), 1)) {
            throw new BrokenConfigurationException("Broken exception for file " + destination.getPath());
        }
        try {
            String destinationPath = formatPath(destination.getPath(), file.getFileName());
//            Files.move(Paths.get(file.getPath()), Paths.get(destinationPath));
            getStorage().getFileHandler().move(file, destination);
            getStorage().getConfiguration().moveCountForDir(file.getPath(), destinationPath);
        } catch (Exception e) {
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
        if(!getStorage().fileCountCheck(destinationPath, list.size())) {
            throw new BrokenConfigurationException("Broken exception for file " + destinationPath);
        }
        for(String path: list) {
            moveFile(new SpecFile(path), new SpecFile(destinationPath));
        }
    }

    @Override
    public void copyFile(SpecFile file, SpecFile destination) throws BrokenConfigurationException, IllegalDestinationException {
        if(!isAncestor(getStorage().getPath(), destination.getPath())) {
            throw new IllegalDestinationException("Illegal destination " + file.getPath() + " " + destination.getPath());
        }
        if(!getStorage().fileCountCheck(destination.getPath(), 1)) {
            throw new BrokenConfigurationException("Broken exception for file " + destination.getPath());
        }
        try {
            String destinationPath = formatPath(destination.getPath(), file.getFileName());
            getStorage().getFileHandler().copy(file, destination);
            getStorage().getConfiguration().copyCountForDir(file.getPath(), destinationPath);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void copyFile(SpecFile file, String destination) throws Exception {
        copyFile(file, new SpecFile(destination));
    }

    @Override
    public void copyFile(String path, String destinationPath) throws Exception{
        moveFile(new SpecFile(path), new SpecFile(destinationPath));
    }

    @Override
    public void copyFiles(List<String> list, String destinationPath) throws Exception {
        if(!getStorage().fileCountCheck(destinationPath, list.size())) {
            throw new BrokenConfigurationException("Broken exception for file " + destinationPath);
        }
        for(String path: list) {
            moveFile(new SpecFile(path), new SpecFile(destinationPath));
        }
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
    public void removeFile(String filePath) throws Exception{
        removeFile(new SpecFile(filePath));
    }

    @Override
    public void removeFile(SpecFile file) throws Exception{
        try {
            getStorage().getFileHandler().delete(file);
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
    public boolean rename(SpecFile file, String newName) throws FileNotFoundCustomException {
        if(!getStorage().getFileHandler().rename(file, newName))
            return false;
        getStorage().getConfiguration().moveCountForDir(file.getPath(), getParentPath(file.getPath())+newName);
        return true;
    }

    @Override
    public boolean rename(String sourcePath, String newName) throws FileNotFoundCustomException {
        return rename(new SpecFile(sourcePath), newName);
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
        List<SpecFile> returnList = new ArrayList<>(specFiles);
        for(SpecFile sf : specFiles){
            returnList.addAll(returnDirectoryFiles(sf.getPath()));
        }
        return returnList;
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
    public boolean containsFile(String dirPath, List<String> fileNames) throws Exception {
        List<SpecFile> filesInDir = returnDirectoryFiles(dirPath);
        List<String> fileNamesInDir = new ArrayList<>();
        for(SpecFile sf: filesInDir){
            fileNamesInDir.add(sf.getFileName());
        }
        for(String fileName : fileNames){
            if(!fileNamesInDir.contains(fileName)) return false;
        }
        return true;
    }

    @Override
    public String returnFileLocation(String s) throws Exception {
        String path = getStorage().getPath();
        return dfs(path,s);
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

    private String dfs(String path, String name) throws Exception {
        String newPath = null;
        List<SpecFile> fileList = returnDirectoryFiles(path);
        List<String> fileNamesInDir = new ArrayList<>();
//        if(fileList == null)
//            throw new RuntimeException();
        if(fileList != null){
            for(SpecFile sf : fileList){ fileNamesInDir.add(sf.getFileName());}
            if (fileNamesInDir.contains(name)) return formatPath(path,name);
            else {
                for(String name2 : fileNamesInDir){
                    String path2 = formatPath(path, name2);
                    newPath = dfs(path2, name);
                    if (newPath != null) return newPath;
                }
            }
        }

        return null;
    }


    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

}
