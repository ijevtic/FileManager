package org.example;

import org.apache.commons.io.FileUtils;
import org.raf.specification.Configuration;
import org.raf.specification.FileManager;
import org.raf.specification.SpecFile;
import org.raf.specification.Storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.example.Constants.*;

public class Implementation extends FileManager{
    @Override
    public void createStorage() {
        createStorage(CONFIGURATION, STORAGE_PATH, STORAGE_NAME);
    }
    @Override
    public void createStorage(String path, String name) {
        createStorage(CONFIGURATION, path, name);
    }
    @Override
    public void createStorage(String name) {
        createStorage(CONFIGURATION, STORAGE_PATH, name);
    }
    @Override
    public void createStorage(Configuration configuration) {
        createStorage(configuration, STORAGE_PATH, STORAGE_NAME);
    }
    @Override
    public void createStorage(Configuration configuration, String name) {
        createStorage(configuration, STORAGE_PATH, name);
    }
    @Override
    public void createStorage(Configuration configuration, String path, String name) {
        String storagePath = path + name;
        createDir(storagePath);
        setStorage(new Storage(storagePath, configuration));
    }

    @Override
    public void createDirectory(String path, String pattern) {
        createDir(path + "dir");
    }

    @Override
    public void addFiles(List<File> fileList, String destinationPath) {
        File dest = new File(destinationPath);
        for(File f : fileList) {
            try {
                FileUtils.copyToDirectory(f, dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeFile(String filePath) throws RuntimeException{
        removeFile(new File(filePath));
    }

    @Override
    public void removeFile(File file) throws RuntimeException{
        try {
            FileUtils.delete(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeFiles(List<String> list) throws RuntimeException{
        for(String path: list) {
            removeFile(new File(path));
        }
    }
    @Override
    public void moveFile(File file, File destination) throws RuntimeException{
        try {
            FileUtils.moveFileToDirectory(file, destination, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void moveFile(File file, String destination) throws RuntimeException {
        moveFile(file, new File(destination));
    }

    @Override
    public void moveFile(String path, String destinationPath) throws RuntimeException{
        moveFile(new File(path), new File(destinationPath));
    }

    @Override
    public void moveFiles(List<String> list, String destinationPath) throws RuntimeException{
        for(String path: list) {
            moveFile(new File(path), new File(destinationPath));
        }
    }

    @Override
    public void download(File source, File destination) throws RuntimeException{
        try {
            FileUtils.copyToDirectory(source, destination);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void download(File source, String destinationPath) throws RuntimeException{
        download(source, new File(destinationPath));
    }


    @Override
    public void download(String sourcePath, String destinationPath) throws RuntimeException{
        download(new File(sourcePath), new File(destinationPath));
//        File f = new File("");
//        f.renameTo()
    }

    //TODO check flag
    @Override
    public void rename(File file, String newName) {
        File newFile = new File(file.getParentFile().getPath() + "/" + newName);
        boolean flag = file.renameTo(newFile);
    }

    @Override
    public void rename(String sourcePath, String newName) {
        rename(new File(sourcePath), newName);
    }

    private void createDir(String path) {
        try {
            Path dirPath = Paths.get(path);

            Files.createDirectories(dirPath);

            System.out.printf("Directory on path %s is created!%n", path);

        } catch (IOException e) {

            System.err.println("Failed to create directory!" + e.getMessage());
        }
    }
}
