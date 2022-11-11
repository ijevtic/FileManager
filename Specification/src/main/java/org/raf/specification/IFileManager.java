package org.raf.specification;

import org.raf.exceptions.BrokenConfigurationException;
import org.raf.exceptions.IllegalDestinationException;

import java.io.IOException;
import java.nio.file.attribute.FileTime;
import java.util.List;

interface IFileManager {
    void createStorage() throws Exception;
    void createStorage(String path, String name) throws Exception;
    void createStorage(String name) throws Exception;
    void createStorage(Configuration c) throws Exception;
    void createStorage(Configuration c, String name) throws Exception;
    void createStorage(Configuration c, String rootPath, String name) throws Exception;
    void loadStorage(String path) throws IOException, BrokenConfigurationException;
    void createDirectory(String rootPath) throws Exception;
    void createDirectory(String rootPath, int fileCount) throws Exception;
    void createDirectory(String rootPath, String pattern) throws Exception;
    void createDirectory(String rootPath, String pattern, int maxFileCount) throws Exception;
    void addFiles(List<SpecFile> files, String destination) throws Exception;
    void removeFile(String path) throws Exception;
    void removeFile(SpecFile file) throws Exception;
    void removeFiles(String[] files) throws Exception;
    void removeFiles(SpecFile[] files) throws Exception;
    void moveFile(SpecFile file, SpecFile destination) throws BrokenConfigurationException, IllegalDestinationException;
    void moveFile(SpecFile file, String destinationPath) throws Exception;
    void moveFile(String filePath, String destinationPath) throws Exception;
    void moveFiles(List<String> files, String destinationPath) throws Exception;
    void download(SpecFile path, SpecFile destinationPath) throws Exception;
    void download(SpecFile path, String destinationPath) throws Exception;
    void download(String path, String destinationPath) throws Exception;
    boolean rename(SpecFile file, String newName)throws Exception;
    boolean rename(String sourcePath, String newName) throws Exception;
    List<SpecFile> returnDirectoryFiles(String directoryPath) throws Exception;
    List<SpecFile> returnDirectoryFiles(String directoryPath, String substring) throws Exception;
    List<SpecFile> returnDirectoryFiles(String directoryPath, List<String> extensions) throws Exception;
    List<SpecFile> returnDirectoryFiles(String directoryPath, List<String> extensions, String substring) throws Exception;
    List<SpecFile> returnSubdirectoryFile(String directoryPath) throws Exception;
    List<SpecFile> returnSubdirectoryFile(String directoryPath, String substring) throws Exception;
    List<SpecFile> returnSubdirectoryFile(String directoryPath, List<String> extension) throws Exception;
    List<SpecFile> returnSubdirectoryFile(String directoryPath, List<String> extension, String substring) throws Exception;
    List<SpecFile> returnAllDirectoryFiles(String directoryPath) throws Exception;
    List<SpecFile> returnAllDirectoryFiles(String directoryPath, String substring) throws Exception;
    List<SpecFile> returnAllDirectoryFiles(String directoryPath, List<String> extension) throws Exception;
    List<SpecFile> returnAllDirectoryFiles(String directoryPath, List<String> extension, String substring) throws Exception;
    boolean containsFile(String directoryPath, List<String> fileName) throws Exception;
    String returnFileLocation(String folderPath) throws Exception;
    void sortFiles(SortingCriteria sortingCriteria, List<SpecFile> files) throws Exception;
    List<SpecFile> returnModifiedFiles(String startDate, String endDate, String directoryPath) throws Exception;

    //Wrapper methods
    List<String> returnFileName(List<SpecFile> files);
    List<String> returnFilePath(List<SpecFile> files);
    List<FileTime> returnDateCreated(List<SpecFile> files);
    List<FileTime> returnDateMModified(List<SpecFile> files);
    List<Boolean> returnIfDepository(List<SpecFile> files);
}
