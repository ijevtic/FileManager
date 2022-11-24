package org.example.example;

import com.google.api.services.drive.Drive;
import org.raf.exceptions.BrokenConfigurationException;
import org.raf.specification.*;

import java.io.IOException;
import java.nio.file.attribute.FileTime;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.List;

import static org.example.example.GDriveConnection.getDriveService;
import static org.raf.utils.Utils.formatPath;

public class Implementation extends FileManager {
    private Drive service;
    public Implementation() throws IOException, GeneralSecurityException {
        this.service = getDriveService();
    }

    @Override
    public void createStorage(Configuration configuration, String rootPath, String name) throws RuntimeException, IOException {
        String storagePath = formatPath(rootPath, name);

        setStorage(new StorageImpl(storagePath, configuration, new FileHandlerImplementation(service)));
//        getStorage().getFileHandler().createDirectory(storagePath);
//        try {
//            getStorage().updateConfiguration();
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void loadStorage(String path) throws IOException, BrokenConfigurationException {
        Storage s = new StorageImpl(path);
        s.setFileHandler(new FileHandlerImplementation(service));
        s.readConfiguration();
        setStorage(s);
        System.out.println(getStorage());
    }

    public Drive getService() {
        return service;
    }

    public void setService(Drive service) {
        this.service = service;
    }
}
