package com.application.todo.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Mustafizur Rahman on 16/04/2018.
 */

public class ParseDate {

    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String HOUR_PATTERN = "HH:mm:ss";

    public static String parseDate(Long timestamp, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.US);
        Long now  = (timestamp != null) ? timestamp : new Date().getTime();
        Date date = new Date(now);
        return format.format(date);
    }

}
