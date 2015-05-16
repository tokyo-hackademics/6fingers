package com.fingers.six.elarm.utils;

import android.util.TimeUtils;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Seconds;

public class DateTimeUtils {

    private DateTimeUtils() { }

    private static final LocalDate BASE_TIME = new LocalDate(1, 1, 2000);
    private static final LocalDateTime BASE_DATE_TIME = new LocalDateTime(1, 1, 2000, 0, 0, 0);

    public static int calcDays(LocalDate date) {
        return Days.daysBetween(BASE_TIME, date).getDays();
    }

    public static int calcSecs(LocalDateTime date) {
        return Seconds.secondsBetween(BASE_TIME, date).getSeconds();
    }
}
