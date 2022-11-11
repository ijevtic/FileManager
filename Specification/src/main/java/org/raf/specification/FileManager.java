package org.raf.specification;

import org.raf.exceptions.BrokenConfigurationException;
import org.raf.exceptions.IllegalDestinationException;
import org.raf.utils.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.List;

import static org.raf.utils.Constants.*;
import static org.raf.utils.Utils.formatPath;
import static org.raf.utils.Utils.isAncestor;

public abstract class FileManager implements IFileManager{

    private Storage storage = null;

    @Override
    public void createStorage() throws Exception {
        createStorage(CONFIGURATION, STORAGE_PATH, STORAGE_NAME);
    }

    @Override
    public void createStorage(String path, String name) throws Exception {
        createStorage(CONFIGURATION, path, name);
    }

    @Override
    public void createStorage(String name) throws Exception {
        createStorage(CONFIGURATION, STORAGE_PATH, name);
    }

    @Override
    public void createStorage(Configuration configuration) throws Exception {
        createStorage(configuration, STORAGE_PATH, STORAGE_NAME);
    }

    @Override
    public void createStorage(Configuration configuration, String name) throws Exception {
        createStorage(configuration, STORAGE_PATH, name);
    }

    @Override
    public void createDirectory(String rootPath) throws BrokenConfigurationException {
        createDirectory(rootPath, -1);
    }

    @Override
    public void createDirectory(String rootPath, int maxFileCount) throws BrokenConfigurationException {
        if(!getStorage().fileCountCheck(rootPath, 1)) {
            throw new BrokenConfigurationException("Broken exception for file " + rootPath);
        }
        createAndStoreDirectory(formatPath(rootPath, "dir"), maxFileCount);
    }

    @Override
    public void createDirectory(String rootPath, String pattern) throws Exception{
        createDirectory(rootPath, pattern, -1);
    }

    @Override
    public void createDirectory(String rootPath, String pattern, int maxFileCount) throws Exception {
        List<String> dirNames = Utils.dirNamesFromPattern(pattern);
        if(!getStorage().fileCountCheck(rootPath, dirNames.size())) {
            throw new BrokenConfigurationException("Broken exception for file " + rootPath);
        }
        for(String name: dirNames) {
            createAndStoreDirectory(formatPath(rootPath, name), maxFileCount);
        }
    }

    private void createAndStoreDirectory(String path, int maxFileCount) throws RuntimeException {
        if(!getStorage().getFileHandler().createDirectory(path))
            return;
        if(maxFileCount < 0)
            return;
        getStorage().getConfiguration().addCountForDir(path, maxFileCount);
        try {
            getStorage().updateConfiguration();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void moveFile(SpecFile file, SpecFile destination) throws BrokenConfigurationException, IllegalDestinationException {
        if(!isAncestor(getStorage().getPath(), destination.getPath())) {
            throw new IllegalDestinationException("Illegal destination " + file.getPath() + " " + destination.getPath());
        }
        if(!getStorage().fileCountCheck(destination.getPath(), 1)) {
            throw new BrokenConfigurationException("Broken exception for file " + destination.getPath());
        }
        try {
            String destinationPath = formatPath(destination.getPath(), file.getFileName());
//            Files.move(Paths.get(file.getPath()), Paths.get(destinationPath));
            getStorage().getFileHandler().move(file, destination);
            getStorage().getConfiguration().moveCountForDir(file.getPath(), destinationPath);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void moveFile(SpecFile file, String destination) throws Exception {
        moveFile(file, new SpecFile(destination));
    }

    @Override
    public void moveFile(String path, String destinationPath) throws Exception{
        moveFile(new SpecFile(path), new SpecFile(destinationPath));
    }

    @Override
    public void moveFiles(List<String> list, String destinationPath) throws Exception {
        if(!getStorage().fileCountCheck(destinationPath, list.size())) {
            throw new BrokenConfigurationException("Broken exception for file " + destinationPath);
        }
        for(String path: list) {
            moveFile(new SpecFile(path), new SpecFile(destinationPath));
        }
    }



    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

}
