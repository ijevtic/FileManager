package org.example;

import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Utils {

    public static LocalDateTime convertToLocalDateTime(FileTime fileTime){
       return LocalDateTime.ofInstant(fileTime.toInstant(), ZoneId.systemDefault());
    }
}
