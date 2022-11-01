package org.raf.specification;

import java.util.List;

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
    public abstract void createStorage(Configuration c, String path, String name) throws Exception;
    public abstract void createDirectory(String path, String pattern) throws Exception;
    public abstract void addFiles(List<SpecFile> files, String destination) throws Exception;
    public abstract void removeFile(String path) throws Exception;
    public abstract void removeFile(SpecFile file) throws Exception;
    public abstract void removeFiles(List<String> files) throws Exception;
    public abstract void moveFile(SpecFile file, SpecFile destination) throws Exception;
    public abstract void moveFile(SpecFile file, String destinationPath) throws Exception;
    public abstract void moveFile(String filePath, String destinationPath) throws Exception;
    public abstract void moveFiles(List<String> files, String destinationPath) throws Exception;
    public abstract void download(SpecFile path, SpecFile destinationPath) throws Exception;
    public abstract void download(SpecFile path, String destinationPath) throws Exception;
    public abstract void download(String path, String destinationPath) throws Exception;
    public abstract boolean rename(SpecFile file, String newName);
    public abstract boolean rename(String sourcePath, String newName);

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
