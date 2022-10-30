package org.raf.specification;

public class Storage {
    private String path;
    private Configuration configuration;

    private volatile static Storage instance = null;
    public Storage() {}

    public Storage(String path, Configuration configuration) {
        this.path = path;
        this.configuration = configuration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
