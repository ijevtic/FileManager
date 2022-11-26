package org.raf.specification;

import org.raf.exceptions.BrokenConfigurationException;
import org.raf.exceptions.FileNotFoundCustomException;
import org.raf.utils.Utils;

import java.io.IOException;

public class StorageProvider {
    private static StorageManager storageManager;

    public static void registerStorageManager(StorageManager fm){
        storageManager = fm;
    }

<<<<<<< HEAD
    public static StorageManager getStorageManager(String path, String name, String loadStorage) throws Exception {
=======
    public static StorageManager getStorageManager(String path, String name, String loadStorage) throws BrokenConfigurationException, FileNotFoundCustomException, IOException {
>>>>>>> 7c26d98c13f984dd3e27f9f6eade8ff4172f9b2f
        if(loadStorage.equals("0"))
            storageManager.createStorage(path, name);
        else
            storageManager.loadStorage(Utils.formatPath(path, name));
        return storageManager;
    }
}
