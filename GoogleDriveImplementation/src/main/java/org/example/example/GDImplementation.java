package org.example.example;

import com.google.api.services.drive.Drive;
import org.raf.exceptions.BrokenConfigurationException;
import org.raf.exceptions.FileNotFoundCustomException;
import org.raf.specification.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static org.example.example.GDriveConnection.getDriveService;
import static org.raf.utils.Utils.formatPath;

public class GDImplementation extends StorageManager {

    static {
        StorageProvider.registerStorageManager(new GDImplementation());
    }
    private Drive service;
    public GDImplementation(){
        try {
            this.service = getDriveService();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createStorage(Configuration configuration, String rootPath, String name) throws FileNotFoundCustomException {
        String storagePath = formatPath(rootPath, name);

        setStorage(new StorageImpl(storagePath, configuration, new FileHandlerImplementation(service)));
        getStorage().getFileHandler().createDirectory(storagePath);
        try {
            getStorage().updateConfiguration();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadStorage(String path) throws BrokenConfigurationException {
        Storage s = new StorageImpl(path, new Configuration());
        s.setFileHandler(new FileHandlerImplementation(service));
        s.readConfiguration();
        setStorage(s);
    }

    public Drive getService() {
        return service;
    }

    public void setService(Drive service) {
        this.service = service;
    }
}
