package org.example.example;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.raf.exceptions.FileNotFoundCustomException;
import org.raf.specification.FileHandler;
import org.raf.specification.SpecFile;
import org.raf.utils.Utils;

import java.io.IOException;
import java.util.List;
public class FileHandlerImplementation extends FileHandler {
    private Drive service;

    public FileHandlerImplementation(Drive service) {
        this.service = service;
    }

    @Override
    public void copy(SpecFile source, SpecFile destination) throws IOException, FileNotFoundCustomException {
        File sourceFile = getFileFromPath(source.getPath());
        String newParentId = getFileFromPath(destination.getPath()).getId();
        File newFile = new File();
        newFile.setParents(List.of(newParentId));
        try {
            // Copy the file to the new folder
            service.files().copy(sourceFile.getId(), newFile)
                .setFields("id, parents")
                .execute();

        } catch (GoogleJsonResponseException e) {
            System.err.println("Unable to copy a file: " + e.getDetails());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void move(SpecFile source, SpecFile destination) throws IOException, FileNotFoundCustomException {
        String sourceId = getFileFromPath(source.getPath()).getId();
        String newParentId = getFileFromPath(destination.getPath()).getId();
        File file = service.files().get(sourceId)
                .setFields("parents")
                .execute();
        StringBuilder previousParents = new StringBuilder();
        for (String parent : file.getParents()) {
            previousParents.append(parent);
            previousParents.append(',');
        }
        try {
            // Move the file to the new folder
            file = service.files().update(sourceId, null)
                    .setAddParents(newParentId)
                    .setRemoveParents(previousParents.toString())
                    .setFields("id, parents")
                    .execute();

        } catch (GoogleJsonResponseException e) {
            System.err.println("Unable to move a file: " + e.getDetails());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean rename(SpecFile specFile, String newName) throws FileNotFoundCustomException{
        File file = getFileFromPath(specFile.getPath());
        File newFile = new File();
        newFile.setName(newName);
        try {
            service.files().update(file.getId(), newFile)
                    .setFields("name")
                    .execute();
            return true;
        } catch (GoogleJsonResponseException e) {
            System.err.println("Unable to rename a file: " + e.getDetails());
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean createDirectory(String path) throws RuntimeException, FileNotFoundCustomException {
        File fileMetadata = new File();

        File parentFile = getFileFromPath(Utils.getParentPath(path));

        fileMetadata.setName(Utils.getNameFromPath(path));
        fileMetadata.setParents(List.of(parentFile.getId()));
        fileMetadata.setMimeType("application/vnd.google-apps.folder");
        try {
            File file = service.files().create(fileMetadata)
                    .setFields("id")
                    .execute();
            System.out.println("Folder ID: " + file.getId());
            return true;
        } catch (GoogleJsonResponseException e) {
            System.err.println("Unable to create folder: " + e.getDetails());
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(SpecFile specFile) throws IOException, FileNotFoundCustomException {
        File sourceFile = getFileFromPath(specFile.getPath());
        try {
            service.files().delete(sourceFile.getId()).execute();
        } catch (GoogleJsonResponseException e) {
            System.err.println("Unable to delete a file: " + e.getDetails());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File getFileFromPath(String filePath) throws FileNotFoundCustomException {
        if(filePath.charAt(0) == '/') filePath = filePath.substring(1);
        if(filePath.charAt(filePath.length()-1) == '/') filePath = filePath.substring(0, filePath.length()-1);

        String[] fileNames = filePath.split("/");
        String parentId = "root";
        File returnFile = null;
        for(int i = 0; i < fileNames.length; i++) {
            String name = fileNames[i];
            FileList result = null;
            String query = "trashed = false and '" + parentId + "' in parents and name = '" + name + "'";
            try {
                result = service.files().list()
                        .setPageSize(1)
                        .setQ(query)
                        .setFields("nextPageToken, files(id, name, createdTime, modifiedTime, parents)")
                        .execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(result.getFiles().isEmpty()) {
                //TODO
                System.out.println("nije nasao");
                throw new FileNotFoundCustomException("File not found on path" + filePath);
            }
            File f = result.getFiles().get(0);
            parentId = f.getId();
            returnFile = f;

        }
        assert returnFile != null;
        return returnFile;
    }

    private File uploadData(String path, String parentId) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName("upload data");
        fileMetadata.setParents(List.of(parentId));
        java.io.File filePath = new java.io.File(path);
        FileContent content = new FileContent("", filePath);
        try {
            File file = service.files().create(fileMetadata, content)
                    .setFields("id, parents")
                    .execute();
            System.out.println("File ID: " + file.getId());
            return file;
        } catch (GoogleJsonResponseException e) {
            System.err.println("Unable to upload file: " + e.getDetails());
            throw e;
        }
    }
}
