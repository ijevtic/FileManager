package org.example.example;

import com.google.api.client.util.DateTime;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class GUtils {
    public static LocalDateTime convertTime(DateTime time) {
        long unixTime = time.getValue();
        LocalDateTime triggerTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(unixTime),
                        TimeZone.getDefault().toZoneId());
        return triggerTime;
    }
}
