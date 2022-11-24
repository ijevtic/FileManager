package org.raf.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static List<String> dirNamesFromPattern(String pattern) {
        List<String> names = new ArrayList<>();
        int indOpen = pattern.indexOf("{");
        int indClosed = pattern.lastIndexOf("}");
        if(indClosed < indOpen || indClosed == -1 || indOpen == -1) return List.of(pattern);
        String prefix = pattern.substring(0, indOpen);
        String suffix = pattern.substring(indClosed+1);
        String inner = pattern.substring(indOpen+1, indClosed);
        if(inner.contains(",")) {
            String [] arr = inner.split(",");
            return Arrays.stream(arr).map(s -> prefix+s+suffix).collect(Collectors.toCollection(ArrayList::new));
        }
        if(!inner.contains("-"))
            return List.of(pattern);
        String [] nums = inner.split("-");

        if(nums.length != 2 || !isNumeric(nums[0]) || !isNumeric(nums[1]))
            return List.of(pattern);
        int a = Integer.parseInt(nums[0]);
        int b = Integer.parseInt(nums[1]);
        int curr = a;
        int diff = (b-a)/(Math.abs(a-b));
        for(int i = 0; i < Math.abs(a-b)+1; i++) {
            names.add(prefix + curr + suffix);
            curr += diff;
        }
        return names;
    }

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static String getExtension(String s) {
        int dot = s.lastIndexOf(".");
        if(dot == -1)
            return null;
        return s.substring(dot+1);
    }

    public static String removeSlash(String path) {
        if(path.equals("/"))
            return "";
        if(path.charAt(0) == '/') path = path.substring(1);
        if(path.charAt(path.length()-1) == '/') path = path.substring(0, path.length()-1);
        return path;
    }

    public static String getParentPath(String path) {
        if (path.charAt(path.length()-1) == '/')
            path = path.substring(0, path.length()-1);
        int poz = path.lastIndexOf("/");
        if(poz == -1) return path + "/";
        return path.substring(0, poz) + "/";
    }

    public static String getNameFromPath(String path) {
        if (path.charAt(path.length() - 1) == '/')
            path = path.substring(0, path.length() - 1);
        return path.substring(path.lastIndexOf("/") + 1);
    }

    public static boolean isAncestor(String parent, String child) {
       return removeSlash(parent).contains(removeSlash(getParentPath(child))) ||
            comparePaths(parent, child);
    }

    public static boolean comparePaths(String path1, String path2) {
        return removeSlash(path1).equals(removeSlash(path2));
    }

    public static String formatPath(String prefixPath, String fileName) {
        String path = prefixPath;
        if (path.charAt(path.length() - 1) != '/')
            path += '/';
        path += fileName;
        return path;
    }

    public static String makePathFromParentPath(String parentPath, String fileName) {
        if(parentPath.charAt(parentPath.length()-1) != '/')
            parentPath += "/";
        return parentPath + fileName;
    }
}
