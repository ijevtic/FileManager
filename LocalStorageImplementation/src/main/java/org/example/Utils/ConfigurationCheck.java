package org.example.Utils;

import org.raf.specification.SpecFile;
import org.raf.specification.Storage;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static org.example.Utils.StringUtils.*;

public class ConfigurationCheck {
//    public static boolean extensionCheck(Storage storage, List<SpecFile> files) {
//        for(SpecFile file:files) {
//            List<String> forbiddenExtensions = storage.getConfiguration().getForbiddenExtensions();
//            String extension = getExtension(file.getPath());
//            if(extension == null)
//                continue;
//            List<String> res = forbiddenExtensions.stream().filter(s -> s.equals(extension)).toList();
//            if(res.size() > 0)
//                return false;
//        }
//        return true;
//    }
//
//    public static boolean fileSizeCheck(Storage storage, List<SpecFile> files) {
//        File storageFile = new File(storage.getPath());
//        int maxByteSize = storage.getConfiguration().getByteSize();
//        int totalNewSize = 0;
//        for(SpecFile specFile:files) {
//            File f = new File(specFile.getPath());
//            totalNewSize += f.length();
//            if(totalNewSize + storageFile.length() > maxByteSize)
//                return false;
//        }
//        return true;
//    }
//
//    public static boolean fileCountCheck(Storage storage, String dirPath, int cntNewFiles){
//        File dir = new File(dirPath);
//        int maxFileCount;
//        if(comparePaths(dirPath, storage.getPath()))
//            maxFileCount = storage.getConfiguration().getGlobalFileCount();
//        else {
//            maxFileCount = storage.getConfiguration().getFileCountForDir(dirPath);
//        }
//        return maxFileCount == -1 || Objects.requireNonNull(dir.list()).length + cntNewFiles <= maxFileCount;
//    }
//
//    public static boolean isAncestor(String parent, String child) {
//        return removeSlash(parent).contains(removeSlash(getParentPath(child))) ||
//                comparePaths(parent, child);
//    }
//
//    private static boolean comparePaths(String path1, String path2) {
//        return removeSlash(path1).equals(removeSlash(path2));
//    }


}
