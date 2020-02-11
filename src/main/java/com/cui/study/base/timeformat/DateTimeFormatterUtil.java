package com.cui.study.base.timeformat;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * https://blog.csdn.net/qq_31866793/article/details/103381980
 * https://www.cnblogs.com/ark-blog/p/9694950.html
 *
 * Instant         时间戳
 * Duration        持续时间、时间差
 * LocalDate       只包含日期，比如：2018-09-24
 * LocalTime       只包含时间，比如：10:32:10
 * LocalDateTime   包含日期和时间，比如：2018-09-24 10:32:10
 * Peroid          时间段
 * ZoneOffset      时区偏移量，比如：+8:00
 * ZonedDateTime   带时区的日期时间
 * Clock           时钟，可用于获取当前时间戳
 * java.time.format.DateTimeFormatter      时间格式化类
 *
 */
public class DateTimeFormatterUtil {

    public static String timestamp2Str(Long milliSecond, String pattern){
        return localDateTime2Str(timestamp2LocalDateTime(milliSecond),pattern);
    }

    public static Date timestamp2Date(Long milliSecond) {
        return localDateTime2Date(timestamp2LocalDateTime(milliSecond));
    }

    public static LocalDateTime timestamp2LocalDateTime(Long milliSecond) {
        Instant instant = Instant.ofEpochMilli(milliSecond);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static String localDateTime2Str(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    public static LocalDateTime str2LocalDateTime(String str, String pattern) {
        return LocalDateTime.parse(str, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime date2LocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static String localDate2Str(LocalDate localDate, String pattern) {
        return localDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String date2FormatterStr(Date date, String pattern) {
        return localDateTime2Str(date2LocalDateTime(date), pattern);
    }

    public static Date anyStr2Date(String input) {
        return localDateTime2Date(anyStr2LocalDateTime(input));
    }

    public static LocalDateTime anyStr2LocalDateTime(String input) {
        String[] arr = input.split(" ");
        return LocalDateTime.of(str2LocalDate(arr[0]), str2LocalTime(arr[1]));
    }

    public static LocalDate str2LocalDate(String str) {
        String[] arr = str.split("-");
        return LocalDate.of(str2Int(arr[0]), str2Int(arr[1]), str2Int(arr[2]));
    }

    public static LocalTime str2LocalTime(String str) {
        String[] arr = str.split(":");
        return LocalTime.of(str2Int(arr[0]), str2Int(arr[1]), str2Int(arr[2]));
    }

    private static int str2Int(String str) {
        return Integer.parseInt(str);
    }
}
