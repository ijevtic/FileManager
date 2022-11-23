package org.example.example;

import org.raf.specification.Configuration;
import org.raf.specification.FileHandler;
import org.raf.specification.Storage;

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
    protected long getFileSize(String s) {
        return 0;
    }

    @Override
    protected int getFileCount(String s) {
        return 0;
    }
}
