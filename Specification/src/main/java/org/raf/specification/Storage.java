package org.raf.specification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.raf.exceptions.BrokenConfigurationException;

import java.io.File;
import java.io.IOException;

public class Storage {
    private String path;
    private Configuration configuration;

    private volatile static Storage instance = null;
    public Storage() {}

    public Storage(String path) {
        this.path = path;
    }
    public Storage(String path, Configuration configuration) {
        this.path = path;
        this.configuration = configuration;
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
}
