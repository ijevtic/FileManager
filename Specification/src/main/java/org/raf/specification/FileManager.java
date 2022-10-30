package org.raf.specification;

import java.util.List;

public abstract class FileManager {
    /**
     * Creates an empty folder, this is the root folder of the storage
     */
    public abstract void createStorage();
    public abstract void createStorage(String path, String name);
    public abstract void createStorage(String name);
    public abstract void createStorage(Configuration c);
    public abstract void createStorage(Configuration c, String name);
    public abstract void createStorage(Configuration c, String path, String name);
    public abstract void createDirectory(String path, String pattern);
    public abstract void addFiles(String path, List<SpecFile> files);
    public abstract void removeFile(String path);
    public abstract void moveFiles(List<SpecFile> files, String destinationPath);
    public abstract void download(String path, String destinationPath);
    public abstract void rename();
}
