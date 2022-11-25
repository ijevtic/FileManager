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

    public StorageImpl(String path) {
        super(path);
    }

    public StorageImpl(String path, Configuration configuration) {
        super(path, configuration);
    }

    public StorageImpl(String path, Configuration configuration, FileHandler fileHandler) {
        super(path, configuration, fileHandler);
    }

    @Override
    public void updateConfiguration() throws FileNotFoundCustomException, IOException {
        SpecFile configuration = new SpecFile(getConfigurationPath());
        byte [] data = getFileHandler().getFileInnerData(configuration);
        Gson gson = new Gson();
        String test = gson.toJson(data);
        System.out.println(test);
    }

    @Override
    public Configuration readConfiguration() throws IOException, BrokenConfigurationException {
        return null;
    }

    @Override
    protected long getFileSize(String s) {
        return 0;
    }

}
