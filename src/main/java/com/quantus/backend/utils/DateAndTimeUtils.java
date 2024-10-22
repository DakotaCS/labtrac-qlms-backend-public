package com.quantus.backend.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-09-27
 */
public class DateAndTimeUtils {

    public static String getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.now().format(formatter);
    }

    public static Date getCurrentTimeAsDate() {
        return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Boolean isFirstDateBeforeThenSecondDate(Date date, Date dateToCompareTo) {
        return !dateToLocalDateTime(date).isBefore(dateToLocalDateTime(dateToCompareTo));
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Date addMonthsToCurrentDate(int monthsToAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getCurrentTimeAsDate());
        calendar.add(Calendar.MONTH, monthsToAdd);
        return calendar.getTime();
    }
}
