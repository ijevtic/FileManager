package org.raf.specification;

import org.raf.exceptions.FileNotFoundCustomException;

import java.io.IOException;
import java.util.List;

public abstract class FileHandler {
    public abstract void copy(SpecFile source, SpecFile destination) throws IOException, FileNotFoundCustomException;
    public abstract void move(SpecFile source, SpecFile destination) throws IOException, FileNotFoundCustomException;
    public abstract boolean createDirectory(String path) throws RuntimeException, FileNotFoundCustomException;
    public abstract boolean rename(SpecFile file, String newName) throws FileNotFoundCustomException;
    public abstract void delete(SpecFile file) throws IOException, FileNotFoundCustomException;
//    public abstract List<SpecFile> directoryFiles(String path) throws Exception;

}
