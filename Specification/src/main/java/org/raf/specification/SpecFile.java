package org.raf.specification;

import java.nio.file.attribute.FileTime;

public class SpecFile {
    String path;
    String fileName;
    FileTime dateCreated;
    FileTime dateModified;
    boolean directory;

    public SpecFile(String path, String fileName, FileTime dateCreated, FileTime dateModified, boolean directory) {
        this.path = path;
        this.fileName = fileName;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.directory = directory;
    }

    public SpecFile(String path, String fileName) {
        this.path = path;
        this.fileName = fileName;
    }

    public SpecFile(String path) {
        this.path = path;
    }

    public SpecFile(String path, String fileName, boolean directory) {
        this.path = path;
        this.fileName = fileName;
        this.directory = directory;
    }

    public SpecFile(String fileName, boolean directory) {
        this.fileName = fileName;
        this.directory = directory;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FileTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(FileTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public FileTime getDateModified() {
        return dateModified;
    }

    public void setDateModified(FileTime dateModified) {
        this.dateModified = dateModified;
    }

    public boolean isDirectory() {
        return directory;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
    }
}
