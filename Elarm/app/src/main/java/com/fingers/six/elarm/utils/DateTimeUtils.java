package com.fingers.six.elarm.utils;

import android.util.TimeUtils;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Seconds;

public class DateTimeUtils {

    private DateTimeUtils() {
    }

    private static final LocalDate BASE_TIME = new LocalDate(2000, 1, 1);
    private static final LocalDateTime BASE_DATE_TIME = new LocalDateTime(2000, 1, 1, 0, 0, 0);

    public static int convertToDays(LocalDate date) {
        return Days.daysBetween(BASE_TIME, date).getDays();
    }

    public static int convertToSeconds(LocalDateTime date) {
        return Seconds.secondsBetween(BASE_DATE_TIME, date).getSeconds();
    }
}
