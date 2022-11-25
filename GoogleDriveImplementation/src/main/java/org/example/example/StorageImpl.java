package org.example.example;

import com.google.api.services.drive.model.File;
import com.google.gson.*;
import org.raf.exceptions.BrokenConfigurationException;
import org.raf.exceptions.FileNotFoundCustomException;
import org.raf.specification.Configuration;
import org.raf.specification.FileHandler;
import org.raf.specification.SpecFile;
import org.raf.specification.Storage;

import java.io.IOException;
import java.lang.reflect.Type;

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
    public void updateConfiguration() {
        ((FileHandlerImplementation)getFileHandler()).updateConfiguration(this);
    }

    @Override
    public void readConfiguration() {
        SpecFile configuration = new SpecFile(getConfigurationPath());
        ((FileHandlerImplementation)getFileHandler()).readConfig(configuration, this);
    }

    @Override
    protected long getFileSize(String s) {
        return 0;
    }

}
