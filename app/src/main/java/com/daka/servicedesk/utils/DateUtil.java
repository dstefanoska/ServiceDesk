package com.daka.servicedesk.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Created by Dana on 22-Sep-17.
 */

public class DateUtil {

    public static String dateTimeToIso(DateTime calendar) {
        DateTimeFormatter parser = ISODateTimeFormat.dateHourMinuteSecond();
        return parser.print(calendar);
    }

    public static String currentDateTime() {
        DateTime date = DateTime.now();
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
        return date.toString(dtf);
    }

    public static String currentDate() {
        DateTime date = DateTime.now();
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
        return date.toString(dtf);
    }

    public static int compareTimestamps(String timestamp1, String timestamp2) {
        DateTimeFormatter parser = ISODateTimeFormat.dateHourMinuteSecond();
        DateTime dateTime1 = parser.parseDateTime(timestamp1);
        DateTime dateTime2 = parser.parseDateTime(timestamp2);
        return dateTime2.compareTo(dateTime1);
    }
}

