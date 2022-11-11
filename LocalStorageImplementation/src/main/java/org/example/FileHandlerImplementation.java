package org.example;

import org.raf.specification.FileHandler;
import org.raf.specification.SpecFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.raf.utils.Utils.formatPath;
import static org.raf.utils.Utils.getParentPath;

public class FileHandlerImplementation extends FileHandler {
    @Override
    public void copy(SpecFile source, SpecFile destination) throws IOException {
        String destinationPath = formatPath(destination.getPath(), source.getFileName());
        Files.copy(Paths.get(source.getPath()), Paths.get(destinationPath));
    }

    /**
     *
     * @param source file that needs to be moved
     * @param destination folder of the destination
     * @throws IOException
     */
    @Override
    public void move(SpecFile source, SpecFile destination) throws IOException {
        String destinationPath = formatPath(destination.getPath(), source.getFileName());
        Files.move(Paths.get(source.getPath()), Paths.get(destinationPath));
    }

    @Override
    public boolean createDirectory(String path) throws RuntimeException {
        if(Files.exists(Paths.get(path)))
            return false;
        try {
            new File(path).mkdirs();
            System.out.printf("Directory on path %s is created!%n", path);
            return true;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean rename(SpecFile file, String newName) {
        File oldFile = new File(file.getPath());
        File newFile = new File(getParentPath(file.getPath())+newName);
        return oldFile.renameTo(newFile);
    }

    @Override
    public void delete(SpecFile file) throws IOException {
        Files.delete(Paths.get(file.getPath()));
    }
}
