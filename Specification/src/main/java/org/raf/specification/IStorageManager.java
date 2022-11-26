package org.raf.specification;

import org.raf.exceptions.BrokenConfigurationException;
import org.raf.exceptions.FileNotFoundCustomException;
import org.raf.exceptions.IllegalDestinationException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

interface IStorageManager {
    void createStorage() throws Exception;
    void createStorage(String path, String name) throws Exception;
    void createStorage(String name) throws Exception;
    void createStorage(Configuration c) throws Exception;
    void createStorage(Configuration c, String name) throws Exception;
    void createStorage(Configuration c, String rootPath, String name) throws FileNotFoundCustomException;
    void saveStorage() throws IOException;
    void loadStorage(String path) throws BrokenConfigurationException;
    void createDirectory(String rootPath) throws Exception;
    void createDirectory(String rootPath, int fileCount) throws Exception;
    void createDirectory(String rootPath, String pattern) throws Exception;
    void createDirectory(String rootPath, String pattern, int maxFileCount) throws Exception;
    void addFiles(List<SpecFile> files, String destination) throws Exception;
    void removeFile(String path) throws Exception;
    void removeFile(SpecFile file) throws Exception;
    void removeFiles(String[] files) throws Exception;
    void removeFiles(SpecFile[] files) throws Exception;
    void moveFile(SpecFile file, SpecFile destination) throws BrokenConfigurationException, IllegalDestinationException, FileNotFoundCustomException;
    void moveFile(SpecFile file, String destinationPath) throws Exception;
    void moveFile(String filePath, String destinationPath) throws Exception;
    void moveFiles(List<String> files, String destinationPath) throws Exception;
    void copyFile(SpecFile file, SpecFile destination) throws BrokenConfigurationException, IllegalDestinationException, FileNotFoundCustomException;
    void copyFile(SpecFile file, String destinationPath) throws Exception;
    void copyFile(String filePath, String destinationPath) throws Exception;
    void copyFiles(List<String> files, String destinationPath) throws Exception;
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
    List<SpecFile> sortFiles(SortingCriteria sortingCriteria, List<SpecFile> files) throws Exception;
    List<SpecFile> returnFilesModifiedDuringPeriod(LocalDateTime startDate, LocalDateTime endDate, String directoryPath) throws Exception;
    List<SpecFile> returnFilesCreatedDuringPeriod(LocalDateTime startDate, LocalDateTime endDate, String directoryPath) throws Exception;

    //Wrapper methods
    List<String> returnFileName(List<SpecFile> files);
    List<String> returnFilePath(List<SpecFile> files);
    List<LocalDateTime> returnDateCreated(List<SpecFile> files);
    List<LocalDateTime> returnDateMModified(List<SpecFile> files);
    List<Boolean> returnIfDepository(List<SpecFile> files);
}
