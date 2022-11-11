package org.raf.specification;

import org.raf.exceptions.BrokenConfigurationException;
import org.raf.exceptions.IllegalDestinationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.List;

import static org.raf.utils.Utils.formatPath;
import static org.raf.utils.Utils.isAncestor;

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
    public abstract void loadStorage(String path) throws IOException, BrokenConfigurationException;
    public abstract void createDirectory(String rootPath) throws Exception;
    public abstract void createDirectory(String rootPath, int fileCount) throws Exception;
    public abstract void createDirectory(String rootPath, String pattern) throws Exception;
    public abstract void createDirectory(String rootPath, String pattern, int maxFileCount) throws Exception;
    public abstract void addFiles(List<SpecFile> files, String destination) throws Exception;
    public abstract void removeFile(String path) throws Exception;
    public abstract void removeFile(SpecFile file) throws Exception;
    public abstract void removeFiles(String[] files) throws Exception;
    public abstract void removeFiles(SpecFile[] files) throws Exception;
    public abstract void moveFile(SpecFile file, String destinationPath) throws Exception;
    public abstract void moveFile(String filePath, String destinationPath) throws Exception;
    public abstract void moveFiles(List<String> files, String destinationPath) throws Exception;
    public abstract void download(SpecFile path, SpecFile destinationPath) throws Exception;
    public abstract void download(SpecFile path, String destinationPath) throws Exception;
    public abstract void download(String path, String destinationPath) throws Exception;
    public abstract boolean rename(SpecFile file, String newName)throws Exception;
    public abstract boolean rename(String sourcePath, String newName) throws Exception;
    public abstract List<SpecFile> returnDirectoryFiles(String directoryPath) throws Exception;
    public abstract List<SpecFile> returnDirectoryFiles(String directoryPath, String substring) throws Exception;
    public abstract List<SpecFile> returnDirectoryFiles(String directoryPath, List<String> extensions) throws Exception;
    public abstract List<SpecFile> returnDirectoryFiles(String directoryPath, List<String> extensions, String substring) throws Exception;
    public abstract List<SpecFile> returnSubdirectoryFile(String directoryPath) throws Exception;
    public abstract List<SpecFile> returnSubdirectoryFile(String directoryPath, String substring) throws Exception;
    public abstract List<SpecFile> returnSubdirectoryFile(String directoryPath, List<String> extension) throws Exception;
    public abstract List<SpecFile> returnSubdirectoryFile(String directoryPath, List<String> extension, String substring) throws Exception;
    public abstract List<SpecFile> returnAllDirectoryFiles(String directoryPath) throws Exception;
    public abstract List<SpecFile> returnAllDirectoryFiles(String directoryPath, String substring) throws Exception;
    public abstract List<SpecFile> returnAllDirectoryFiles(String directoryPath, List<String> extension) throws Exception;
    public abstract List<SpecFile> returnAllDirectoryFiles(String directoryPath, List<String> extension, String substring) throws Exception;
    public abstract boolean containsFile(String directoryPath, List<String> fileName) throws Exception;
    public abstract String returnFileLocation(String folderPath) throws Exception;
    public abstract List<SpecFile> sortFiles(SortingCriteria sortingCriteria, List<SpecFile> files) throws Exception;
    public abstract List<SpecFile> returnFilesModifiedDuringPeriod(FileTime startDate, FileTime endDate, String directoryPath) throws Exception;
    public abstract List<SpecFile> returnFilesCreatedDuringPeriod(FileTime startDate, FileTime endDate, String directoryPath) throws Exception;

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
            getStorage().getFileHandler().move();
            getStorage().getConfiguration().moveCountForDir(file.getPath(), destinationPath);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
