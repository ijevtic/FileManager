package org.example;

import org.raf.exceptions.BrokenConfigurationException;
import org.raf.exceptions.FileNotFoundCustomException;
import org.raf.specification.*;
import java.io.IOException;


public class Implementation extends StorageManager{

    static {
        StorageProvider.registerStorageManager(new Implementation());
    }

    @Override
    public void createStorage(Configuration configuration, String rootPath, String name) throws FileNotFoundCustomException {
        String storagePath = formatPath(rootPath, name);

        setStorage(new StorageImpl(storagePath, configuration, new FileHandlerImplementation()));
        getStorage().getFileHandler().createDirectory(storagePath);
        try {
            getStorage().updateConfiguration();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadStorage(String path) throws BrokenConfigurationException {
        Storage s = new StorageImpl(path, new Configuration());
        s.setFileHandler(new FileHandlerImplementation());
        s.readConfiguration();
        setStorage(s);
        System.out.println(getStorage());
    }

    private String formatPath(String prefixPath, String fileName) {
        String path = prefixPath;
        if(path.charAt(path.length()-1) != '/')
            path += '/';
        path += fileName;
        return path;
    }
}
