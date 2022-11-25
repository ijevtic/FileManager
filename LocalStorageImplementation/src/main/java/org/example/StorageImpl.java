package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.raf.exceptions.BrokenConfigurationException;
import org.raf.specification.Configuration;
import org.raf.specification.FileHandler;
import org.raf.specification.Storage;

import java.io.File;
import java.io.IOException;

public class StorageImpl extends Storage {

    public StorageImpl() {
        super();
    }

    public StorageImpl(String path, Configuration configuration) {
        super(path, configuration);
    }

    public StorageImpl(String path, Configuration configuration, FileHandler fileHandler) {
        super(path, configuration, fileHandler);
    }

    @Override
    public void updateConfiguration() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(getConfiguration().getConfigPath());
        try {
            file.createNewFile();
        }
        catch(Exception e) {
            //TODO
            e.getStackTrace();
        }
        objectMapper.writeValue(file, this);
    }

    @Override
    public void readConfiguration() throws IOException, BrokenConfigurationException {
        ObjectMapper objectMapper = new ObjectMapper();
        File configFile;
        String configurationPath = getConfigurationPath();
        if(getConfiguration() == null)
            configFile = new File(configurationPath);
        else
            configFile = new File(getConfiguration().getConfigPath());
        if(!configFile.createNewFile()) {
            try {
                Storage s = objectMapper.readValue(configFile, new TypeReference<Storage>() {
                });
                setConfiguration(s.getConfiguration());
                getConfiguration().setPathFromStorage(this.getPath());
            }
            catch(IOException exception) {
                throw new BrokenConfigurationException("Configuration file on path " + configurationPath + " is broken");
            }

        }
        else {
            setConfiguration(new Configuration());
            getConfiguration().setPathFromStorage(this.getPath());
            updateConfiguration();
        }
    }

    @Override
    protected long getFileSize(String path) {
        File f = new File(path);
        return f.length();
    }
}
