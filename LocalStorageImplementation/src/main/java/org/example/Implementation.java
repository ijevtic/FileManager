package org.example;

import org.raf.specification.Configuration;
import org.raf.specification.FileManager;
import org.raf.specification.SpecFile;

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
        try {
            Path dirPath = Paths.get(path + name);

            Files.createDirectories(dirPath);

            System.out.println("Storage is created!");

        } catch (IOException e) {

            System.err.println("Failed to create storage!" + e.getMessage());
        }
    }

    @Override
    public void createDirectory(String s, String s1) {

    }

    @Override
    public void addFiles(String s, List<SpecFile> list) {

    }

    @Override
    public void removeFile(String s) {

    }

    @Override
    public void moveFiles(List<SpecFile> list, String s) {

    }

    @Override
    public void download(String s, String s1) {

    }

    @Override
    public void rename() {

    }
}
