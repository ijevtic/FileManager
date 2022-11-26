package org.raf.specification;

import org.raf.utils.Utils;

import java.nio.file.attribute.FileTime;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SpecFile {
    String path;
    String fileName;
    LocalDateTime dateCreated;
    LocalDateTime dateModified;
    boolean directory;


    public SpecFile(String path, String fileName, LocalDateTime dateCreated, LocalDateTime dateModified, boolean directory) {
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
        this.fileName = Utils.getNameFromPath(path);
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

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateModified() {
        return dateModified;
    }

    public void setDateModified(LocalDateTime dateModified) {
        this.dateModified = dateModified;
    }

    public boolean isDirectory() {
        return directory;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
    }

    @Override
    public String toString() {
        return fileName +
                " path: '" + path +
                " dateCreated: " + dateCreated +
                " dateModified: " + dateModified + '\n';
    }
}
