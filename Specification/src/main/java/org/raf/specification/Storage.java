package org.raf.specification;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.raf.exceptions.BrokenConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.raf.utils.Utils.*;

public abstract class Storage {
    private String path;
    private Configuration configuration;
    private FileHandler fileHandler;

    private volatile static Storage instance = null;
    public Storage() {}

    public Storage(String path) {
        this.path = path;
    }
    public Storage(String path, Configuration configuration) {
        this.path = path;
        this.configuration = configuration;
    }

    public Storage(String path, Configuration configuration, FileHandler fileHandler) {
        this.path = path;
        this.configuration = configuration;
        this.fileHandler = fileHandler;
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

    public void updateConfiguration() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(configuration.getPath());
        objectMapper.writeValue(file, this);
    }

    public Configuration readConfiguration() throws IOException, BrokenConfigurationException {
        ObjectMapper objectMapper = new ObjectMapper();
        File configFile;
        String configurationPath = getConfigurationPath();
        if(configuration == null)
            configFile = new File(configurationPath);
        else
            configFile = new File(configuration.getPath());
        if(!configFile.createNewFile()) {
            try {
                Storage s = objectMapper.readValue(configFile, new TypeReference<Storage>() {
                });
                setConfiguration(s.configuration);
                configuration.setPath(configurationPath);
            }
            catch(IOException exception) {
                throw new BrokenConfigurationException("Configuration file on path " + configurationPath + " is broken");
            }

        }
        else {
            setConfiguration(new Configuration());
            configuration.setPath(configurationPath);
            updateConfiguration();
        }

        return configuration;
    }

    private String getConfigurationPath() {
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

    protected abstract int getFileCount(String filePath);

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

    public boolean fileCountCheck(String dirPath, int cntNewFiles){
        int maxFileCount;
        if(comparePaths(dirPath, this.getPath()))
            maxFileCount = this.getConfiguration().getGlobalFileCount();
        else
            maxFileCount = this.getConfiguration().getFileCountForDir(dirPath);
        return maxFileCount == -1 || getFileCount(dirPath) + cntNewFiles <= maxFileCount;
    }
}
