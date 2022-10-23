package org.raf.specification;

import java.util.ArrayList;
import java.util.List;

public class Configuration {
    int byteSize;
    List<String> forbiddenFiles;
    int fileCount;
    public Configuration(int byteSize, List<String> forbiddenFiles, int fileCount) {
        this.byteSize = byteSize;
        this.forbiddenFiles = new ArrayList<>(forbiddenFiles);
        this.fileCount = fileCount;
    }
}
