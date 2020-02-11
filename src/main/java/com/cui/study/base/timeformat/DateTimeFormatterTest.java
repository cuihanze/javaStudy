package com.cui.study.base.timeformat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeFormatterTest {
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static void main(String[] args) throws ParseException {
        String time = "2019-1-1 10:19:9";
        Date date = DateTimeFormatterUtil.anyStr2Date(time);
        System.out.println(date);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT);
        Date date1 = simpleDateFormat.parse(time);
        System.out.println(date1);

    }


}
