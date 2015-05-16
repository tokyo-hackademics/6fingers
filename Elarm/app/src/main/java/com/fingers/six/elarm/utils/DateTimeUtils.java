package com.fingers.six.elarm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateTimeUtils {

    private DateTimeUtils() { }

    public static long CalcDays(Date date) throws ParseException {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
        Date date1 = myFormat.parse("01 01 2000");
        long diff = date.getTime() - date1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
}
