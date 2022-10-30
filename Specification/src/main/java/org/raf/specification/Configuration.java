package org.raf.specification;

import java.util.ArrayList;
import java.util.List;

public class Configuration {
    private int byteSize;
    private List<String> forbiddenExtensions;
    private int fileCount;
    public Configuration(int byteSize, List<String> forbiddenFiles, int fileCount) {
        this.byteSize = byteSize;
        this.forbiddenExtensions = new ArrayList<>(forbiddenFiles);
        this.fileCount = fileCount;
    }

    public int getByteSize() {
        return byteSize;
    }

    public void setByteSize(int byteSize) {
        this.byteSize = byteSize;
    }

    public List<String> getForbiddenExtensions() {
        return forbiddenExtensions;
    }

    public void setForbiddenExtensions(List<String> forbiddenExtensions) {
        this.forbiddenExtensions = forbiddenExtensions;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }
}
