package com.pzh.zp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Stamp2date {
    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static int compareToCurrentTime(Date date) {
        int num = 0;
        //当前时间
        long currentTime = System.currentTimeMillis();
        long argTime = date.getTime();
        if (currentTime == argTime) {
            num = 0;
        } else if (argTime > currentTime) {
            num = 1;
        } else {
            num = -1;
        }
        return num;
    }
}
