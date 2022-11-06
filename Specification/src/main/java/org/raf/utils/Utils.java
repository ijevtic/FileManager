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
        if(indClosed < indOpen) return List.of(pattern);
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
}
