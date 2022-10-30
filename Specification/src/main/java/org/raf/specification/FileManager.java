package org.raf.specification;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class FileManager {

    private Storage storage = null;

    /**
     * Creates an empty folder, this is the root folder of the storage
     */
    public abstract void createStorage() throws RuntimeException;
    public abstract void createStorage(String path, String name) throws RuntimeException;
    public abstract void createStorage(String name) throws RuntimeException;
    public abstract void createStorage(Configuration c) throws RuntimeException;
    public abstract void createStorage(Configuration c, String name) throws RuntimeException;
    public abstract void createStorage(Configuration c, String path, String name) throws RuntimeException;
    public abstract void createDirectory(String path, String pattern) throws RuntimeException;
    public abstract void addFiles(List<File> files, String destination) throws RuntimeException;
    public abstract void removeFile(String path) throws RuntimeException;
    public abstract void removeFile(File file) throws RuntimeException;
    public abstract void removeFiles(List<String> files) throws RuntimeException;
    public abstract void moveFile(File file, File destination) throws RuntimeException;
    public abstract void moveFile(File file, String destinationPath) throws RuntimeException;
    public abstract void moveFile(String filePath, String destinationPath) throws RuntimeException;
    public abstract void moveFiles(List<String> files, String destinationPath) throws RuntimeException;
    public abstract void download(File path, File destinationPath) throws RuntimeException;
    public abstract void download(File path, String destinationPath) throws RuntimeException;
    public abstract void download(String path, String destinationPath) throws RuntimeException;
    public abstract void rename(File file, String newName);
    public abstract void rename(String sourcePath, String newName);

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
