package org.raf.specification;

import org.raf.exceptions.BrokenConfigurationException;
import org.raf.exceptions.FileNotFoundCustomException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.raf.utils.Utils.*;

public abstract class Storage {
    private String path;
    private Configuration configuration;
    private transient FileHandler fileHandler;

    private transient volatile static Storage instance = null;
    public Storage() {}

    public Storage(String path, Configuration configuration) {
        this.path = path;
        this.configuration = configuration;
        this.configuration.setPathFromStorage(path);
    }

    public Storage(String path, Configuration configuration, FileHandler fileHandler) {
        this.path = path;
        this.configuration = configuration;
        this.fileHandler = fileHandler;
        this.configuration.setPathFromStorage(path);
    }

    public FileHandler getFileHandler() {
        return fileHandler;
    }

    public void setFileHandler(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public abstract void updateConfiguration() throws IOException, FileNotFoundCustomException;

    public abstract void readConfiguration() throws IOException, BrokenConfigurationException;

    protected String getConfigurationPath() {
        String res = path;
        if(path.charAt(path.length()-1) != '/')
            res += "/";
        return res + "configuration.json";
    }

    @Override
    public String toString() {
        return "Storage{" +
                "path='" + path + '\'' +
                ", configuration=" + configuration +
                '}';
    }

    public boolean extensionCheck(List<SpecFile> files) {
        for(SpecFile file:files) {
            List<String> forbiddenExtensions = this.getConfiguration().getForbiddenExtensions();
            String extension = getExtension(file.getPath());
            if(extension == null)
                continue;
            List<String> res = forbiddenExtensions.stream().filter(s -> s.equals(extension)).toList();
            if(res.size() > 0)
                return false;
        }
        return true;
    }

    protected abstract long getFileSize(String filePath);

    protected int getFileCount(String path) throws IOException, FileNotFoundCustomException {
        File f = new File(path);
        return getFileHandler().getFilesFromDir(path).size();
    }

    public boolean fileSizeCheck(List<SpecFile> files) {
        int maxByteSize = this.getConfiguration().getByteSize();
        int totalNewSize = 0;
        for(SpecFile specFile:files) {
            totalNewSize += getFileSize(specFile.getPath());
            if(totalNewSize + getFileSize(this.getPath()) > maxByteSize)
                return false;
        }
        return true;
    }

    public boolean fileCountCheck(String dirPath, int cntNewFiles) throws IOException, FileNotFoundCustomException {
        int maxFileCount;
        if(comparePaths(dirPath, this.getPath()))
            maxFileCount = this.getConfiguration().getGlobalFileCount();
        else
            maxFileCount = this.getConfiguration().getFileCountForDir(dirPath);
        return maxFileCount == -1 || getFileCount(dirPath) + cntNewFiles <= maxFileCount;
    }
}
