package org.example;

import org.raf.specification.Configuration;
import org.raf.specification.FileHandler;
import org.raf.specification.Storage;

import java.io.File;
import java.util.Objects;

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
    protected long getFileSize(String path) {
        File f = new File(path);
        return f.length();
    }

    @Override
    protected int getFileCount(String path) {
        File f = new File(path);
        return Objects.requireNonNull(f.list()).length;
    }
}
