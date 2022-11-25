package org.raf.specification;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configuration {
    private int byteSize;
    private List<String> forbiddenExtensions;
    private int globalFileCount;
    private Map<String, Integer> mapFileCount;
    private String configPath;

    public Configuration() {
        this.byteSize = 10000;
        this.forbiddenExtensions = List.of("exe");
        this.globalFileCount = 10;
        this.mapFileCount = new HashMap<>();
    }

    public Configuration(int byteSize, List<String> forbiddenExtensions, int globalFileCount, Map<String, Integer> mapFileCount) {
        this.byteSize = byteSize;
        this.forbiddenExtensions = new ArrayList<>(forbiddenExtensions);
        this.globalFileCount = globalFileCount;
        this.mapFileCount = new HashMap<>(mapFileCount);
    }

    public Configuration(int byteSize, int globalFileCount) {
        this.byteSize = byteSize;
        this.globalFileCount = globalFileCount;
        this.forbiddenExtensions = new ArrayList<>();
        this.mapFileCount = new HashMap<>();
    }

    public Configuration(int byteSize, List<String> forbiddenExtensions, int globalFileCount) {
        this.byteSize = byteSize;
        this.forbiddenExtensions = new ArrayList<>(forbiddenExtensions);
        this.globalFileCount = globalFileCount;
        this.mapFileCount = new HashMap<>();
    }

    public Configuration(int byteSize, int globalFileCount, Map<String, Integer> mapFileCount) {
        this.byteSize = byteSize;
        this.forbiddenExtensions = new ArrayList<>();
        this.globalFileCount = globalFileCount;
        this.mapFileCount = new HashMap<>(mapFileCount);
    }

    public Map<String, Integer> getMapFileCount() {
        return mapFileCount;
    }

    public void setMapFileCount(Map<String, Integer> mapFileCount) {
        this.mapFileCount = mapFileCount;
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

    public void addCountForDir(String dirPath, int maxFileCount) {
        mapFileCount.put(dirPath, maxFileCount);
    }

    public void removeCountForDir(String dirPath) {
        mapFileCount.remove(dirPath);
    }

    public void moveCountForDir(String oldDirPath, String newDirPath) {
        if(!mapFileCount.containsKey(oldDirPath))
            return;
        int val = mapFileCount.get(oldDirPath);
        removeCountForDir(oldDirPath);
        addCountForDir(newDirPath, val);
    }

    public void copyCountForDir(String dirPath, String newDirPath) {
        if(!mapFileCount.containsKey(dirPath))
            return;
        int val = mapFileCount.get(dirPath);
        addCountForDir(newDirPath, val);
    }

    public int getFileCountForDir(String path) {
        if(mapFileCount.containsKey(path))
            return mapFileCount.get(path);
        return -1;
    }

    public int getGlobalFileCount() {
        return globalFileCount;
    }

    public void setGlobalFileCount(int globalFileCount) {
        this.globalFileCount = globalFileCount;
    }

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    public void setPathFromStorage(String storagePath) {
        this.configPath = createPath(storagePath);
    }

    private String createPath(String storagePath) {
        String confName = storagePath;
        if(storagePath.charAt(storagePath.length()-1) != '/')
            confName += '/';
        confName += "configuration.json";
        return confName;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "byteSize=" + byteSize +
                ", forbiddenExtensions=" + forbiddenExtensions +
                ", globalFileCount=" + globalFileCount +
                ", mapFileCount=" + mapFileCount +
                ", configPath='" + configPath + '\'' +
                '}';
    }
}
