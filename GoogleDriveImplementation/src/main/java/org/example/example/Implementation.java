package org.example.example;

import com.google.api.services.drive.Drive;
import org.raf.exceptions.BrokenConfigurationException;
import org.raf.specification.*;

import java.io.IOException;
import java.nio.file.attribute.FileTime;
import java.security.GeneralSecurityException;
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

    @Override
    public List<SpecFile> returnDirectoryFiles(String s)throws Exception {
//        File f = new File(s);
//        String[] fileNames = f.list();
//        List<SpecFile> specFiles = new ArrayList<>();
//        if(fileNames != null){
//            for(String str : fileNames){
//                String path = formatPath(s,str);
//                BasicFileAttributes fileAttributes = Files.readAttributes(Path.of(path), BasicFileAttributes.class);
//                specFiles.add(new SpecFile(path, str, fileAttributes.creationTime(), fileAttributes.lastModifiedTime(), fileAttributes.isDirectory()));
//            }
//        }
//        if(fileNames == null)
//            throw new RuntimeException();
//        for(String str : fileNames){
//            String path = formatPath(s,str);
//            BasicFileAttributes fileAttributes = Files.readAttributes(Path.of(path), BasicFileAttributes.class);
//            specFiles.add(new SpecFile(path, str, fileAttributes.creationTime(), fileAttributes.lastModifiedTime(), fileAttributes.isDirectory()));
//        }

//        return specFiles;
        return null;
    }

    @Override
    public List<SpecFile> sortFiles(SortingCriteria sortingCriteria, List<SpecFile> list) throws Exception {
        return null;
    }

    @Override
    public List<SpecFile> returnFilesModifiedDuringPeriod(FileTime fileTime, FileTime fileTime1, String s) throws Exception {
        return null;
    }

    @Override
    public List<SpecFile> returnFilesCreatedDuringPeriod(FileTime fileTime, FileTime fileTime1, String s) throws Exception {
        return null;
    }

    public Drive getService() {
        return service;
    }

    public void setService(Drive service) {
        this.service = service;
    }
}
