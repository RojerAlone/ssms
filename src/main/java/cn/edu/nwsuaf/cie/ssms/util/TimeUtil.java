package cn.edu.nwsuaf.cie.ssms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    private static final Calendar CALENDAR = Calendar.getInstance();
    
    /**
     * 检测时间是否合法（开始时间在结束时间之前同时时间差是整时的）
     */
    public static boolean checkTime(long startTime, long endTime) {
        return startTime < endTime && (endTime - startTime) % TimeUtil.ONE_HOUR == 0;
    }

    /**
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

    /**
     * 比较两个时间的时分
     */
    public static int compareTime(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("date con't be null");
        }
        try {
            String[] dateStr1 = formatDateTime(date1).split(" ")[1].split(":");
            String[] dateStr2 = formatDateTime(date2).split(" ")[1].split(":");
            int hour1 = Integer.valueOf(dateStr1[0]);
            int hour2 = Integer.valueOf(dateStr2[0]);
            if (hour1 > hour2) {
                return 1;
            } else if (hour1 < hour2) {
                return -1;
            } else {
                int minute1 = Integer.valueOf(dateStr1[1]);
                int minute2 = Integer.valueOf(dateStr2[1]);
                if (minute1 > minute2) {
                    return 1;
                } else if (minute1 < minute2) {
                    return -1;
                } else {
                    return 0;
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("error date format", e);
        }
    }

    /**
     * 获取日期是星期几
     * @param date
     * @return
     */
    public static int getWeekday(Date date) {
        synchronized (CALENDAR) {
            CALENDAR.setTime(date);
            return CALENDAR.get(Calendar.DAY_OF_WEEK);
        }
    }

}
