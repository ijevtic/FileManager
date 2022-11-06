package org.raf.specification;

import javax.swing.*;
import java.io.File;
import java.nio.file.attribute.FileTime;
import java.util.List;
import java.util.SimpleTimeZone;

public abstract class FileManager {

    private Storage storage = null;

    /**
     * Creates an empty folder, this is the root folder of the storage
     */
    public abstract void createStorage() throws Exception;
    public abstract void createStorage(String path, String name) throws Exception;
    public abstract void createStorage(String name) throws Exception;
    public abstract void createStorage(Configuration c) throws Exception;
    public abstract void createStorage(Configuration c, String name) throws Exception;
    public abstract void createStorage(Configuration c, String rootPath, String name) throws Exception;
    public abstract void loadStorage(String path) throws Exception;
    public abstract void createDirectory(String rootPath) throws Exception;
    public abstract void createDirectory(String rootPath, int fileCount) throws Exception;
    public abstract void createDirectory(String rootPath, String pattern) throws Exception;
    public abstract void createDirectory(String rootPath, String pattern, int maxFileCount) throws Exception;
    public abstract void addFiles(List<SpecFile> files, String destination) throws Exception;
    public abstract void removeFile(String path) throws Exception;
    public abstract void removeFile(SpecFile file) throws Exception;
    public abstract void removeFiles(String[] files) throws Exception;
    public abstract void removeFiles(SpecFile[] files) throws Exception;
    public abstract void moveFile(SpecFile file, SpecFile destination) throws Exception;
    public abstract void moveFile(SpecFile file, String destinationPath) throws Exception;
    public abstract void moveFile(String filePath, String destinationPath) throws Exception;
    public abstract void moveFiles(List<String> files, String destinationPath) throws Exception;
    public abstract void download(SpecFile path, SpecFile destinationPath) throws Exception;
    public abstract void download(SpecFile path, String destinationPath) throws Exception;
    public abstract void download(String path, String destinationPath) throws Exception;
    public abstract boolean rename(SpecFile file, String newName);
    public abstract boolean rename(String sourcePath, String newName);
    public abstract List<SpecFile> returnDirectoryFiles(String directoryPath);
    public abstract List<SpecFile> returnDirectoryFiles(String directoryPath, String substring);
    public abstract List<SpecFile> returnDirectoryFiles(String directoryPath, List<String> extensions);
    public abstract List<SpecFile> returnDirectoryFiles(String directoryPath, List<String> extensions, String substring);
    public abstract List<SpecFile> returnSubdirectoryFile(String directoryPath);
    public abstract List<SpecFile> returnSubdirectoryFile(String directoryPath, String substring);
    public abstract List<SpecFile> returnSubdirectoryFile(String directoryPath, List<String> extension);
    public abstract List<SpecFile> returnSubdirectoryFile(String directoryPath, List<String> extension, String substring);
    public abstract List<SpecFile> returnAllDirectoryFiles(String directoryPath);
    public abstract List<SpecFile> returnAllDirectoryFiles(String directoryPath, String substring);
    public abstract List<SpecFile> returnAllDirectoryFiles(String directoryPath, List<String> extension);
    public abstract List<SpecFile> returnAllDirectoryFiles(String directoryPath, List<String> extension, String substring);
    public abstract boolean containsFile(String directoryPath, List<String> fileName);
    public abstract String returnFileLocation(String folderPath);
    public abstract void sortFiles(SortingCriteria sortingCriteria);
    public abstract List<SpecFile> returnModifiedFiles(String startDate, String endDate, String directoryPath);

    //Wrapper methods
    public abstract List<String> returnFileName(List<SpecFile> files);
    public abstract List<String> returnFilePath(List<SpecFile> files);
    public abstract List<FileTime> returnDateCreated(List<SpecFile> files);
    public abstract List<FileTime> returnDateMModified(List<SpecFile> files);
    public abstract List<Boolean> returnIfDepository(List<SpecFile> files);
    //End of Wrapper methods
    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
