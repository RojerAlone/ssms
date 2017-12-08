package cn.edu.nwsuaf.cie.ssms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangrenjie on 2017-12-04
 */
public class TimeUtil {

    public static final long ONE_HOUR = 1000 * 60 * 60;

    public static final long ONE_DAY = ONE_HOUR * 24;

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    private static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm");

    private static final SimpleDateFormat DATETIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * 检测时间是否合法（开始时间在结束时间之前同时时间差是整时的）
     */
    public static boolean checkTime(long startTime, long endTime) {
        return startTime < endTime && (endTime - startTime) % TimeUtil.ONE_HOUR == 0;
    }

    /*
     * SimpleDateFormat 不是线程安全的
     */
    public static String formatDate(Date date) {
        synchronized (DATE_FORMATTER) {
            return DATE_FORMATTER.format(date);
        }
    }

    public static Date parseDate(String date) throws ParseException {
        synchronized (DATE_FORMATTER) {
            return DATE_FORMATTER.parse(date);
        }
    }

    public static String formatTime(Date date) {
        synchronized (TIME_FORMATTER) {
            return TIME_FORMATTER.format(date);
        }
    }

    public static Date parseTime(String date) throws ParseException {
        synchronized (TIME_FORMATTER) {
            return TIME_FORMATTER.parse(date);
        }
    }

    public static String formatDateTime(Date date) {
        synchronized (DATETIME_FORMATTER) {
            return DATETIME_FORMATTER.format(date);
        }
    }

    public static Date parseDateTime(String date) throws ParseException {
        synchronized (DATETIME_FORMATTER) {
            return DATETIME_FORMATTER.parse(date);
        }
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(formatDateTime(parseDateTime("2017-11-11 12:00")));
    }
}
