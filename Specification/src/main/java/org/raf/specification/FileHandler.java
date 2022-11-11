package org.raf.specification;

import java.io.IOException;

public abstract class FileHandler {
    public abstract void copy(SpecFile source, SpecFile destination) throws IOException;
    public abstract void move(SpecFile source, SpecFile destination) throws IOException;
    public abstract boolean createDirectory(String path) throws RuntimeException;
    public abstract boolean rename(SpecFile file, String newName);
    public abstract void delete(SpecFile file) throws IOException;

}
