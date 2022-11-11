package org.example;

import org.raf.specification.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        Implementation im = new Implementation();
        im.createStorage("home/ijevtic/Desktop", "S1");
//        im.loadStorage("/home/ijevtic/Desktop/PredefinedName");
//        File f = new File(im.getStorage().getPath());
//        im.createDirectory(im.getStorage().getPath(), "abc{1-20}d");
//        System.out.println(f.length());

    }

//    private static String dfs(String path, String name){
//        File f = new File(path);
//        String newPath = null;
//        String [] fileList = f.list();
//        if(fileList != null){
//            List<String> fileNames = List.of(fileList);
//            if (fileNames.contains(name)) return formatPath(path,name);
//            else {
//                for(String name2 : fileNames){
//                    String path2 = formatPath(path, name2);
//                    newPath = dfs(path2, name);
//                    if(newPath != null) return newPath;
//                }
//            }
//        }
//        return null;
//    }
//
//    public static List<SpecFile> returnSubdirectoryFile(String s) throws Exception {
//        List<SpecFile> directoryFiles = returnDirectoryFiles(s);
//        List<SpecFile> subdirectoryFiles = new ArrayList<>();
//        for(SpecFile sf : directoryFiles){
//            subdirectoryFiles.addAll(returnDirectoryFiles(sf.getPath()));
//        }
//        return subdirectoryFiles;
//    }
//
//    private static String formatPath(String prefixPath, String fileName) {
//        String path = prefixPath;
//        if(path.charAt(path.length()-1) != '/')
//            path += '/';
//        path += fileName;
//        return path;
//    }
//
//    public static List<SpecFile> returnDirectoryFiles(String s)throws Exception {
//        File f = new File(s);
//        String[] fileNames = f.list();
//        List<SpecFile> specFiles = new ArrayList<>();
//        if(fileNames != null) {
//            for (String str : fileNames) {
//                String path = formatPath(s, str);
//                BasicFileAttributes fileAttributes = Files.readAttributes(Path.of(path), BasicFileAttributes.class);
//                specFiles.add(new SpecFile(path, str, fileAttributes.creationTime(), fileAttributes.lastModifiedTime(), fileAttributes.isDirectory()));
//            }
//        }
//        return specFiles;
//    }
//    public static List<SpecFile> returnFilesWithSubstring(List<SpecFile> specFiles, String substring){
//        List<SpecFile> returnList = new ArrayList<>();
//        for(SpecFile sf : specFiles){
//            String fileName = sf.getFileName();
//            if(fileName.contains(substring)){returnList.add(sf);}
//        }
//        return returnList;
//    }
}