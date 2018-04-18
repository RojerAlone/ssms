package cn.edu.nwsuaf.cie.ssms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhangrenjie on 2017-12-04
 */
@Component
public class TimeUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeUtil.class);

    public static final long ONE_HOUR = 1000 * 60 * 60;

    public static final long ONE_DAY = ONE_HOUR * 24;

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    private static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm");

    private static final SimpleDateFormat DATETIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private static final Calendar CALENDAR = Calendar.getInstance();

    public static String SUMMER_START_TIME;

    public static String SUMMER_END_TIME;

    public static String WINTER_START_TIME;

    public static String WINTER_END_TIME;

    @Value("${properties.time.summer.start}")
    public void setSummerStartTime(String summerStartTime) {
        SUMMER_START_TIME = summerStartTime;
    }

    @Value("${properties.time.summer.end}")
    public void setSummerEndTime(String summerEndTime) {
        SUMMER_END_TIME = summerEndTime;
    }

    @Value("${properties.time.winter.start}")
    public void setWinterStartTime(String winterStartTime) {
        WINTER_START_TIME = winterStartTime;
    }

    @Value("${properties.time.winter.end}")
    public void setWinterEndTime(String winterEndTime) {
        WINTER_END_TIME = winterEndTime;
    }

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

    /**
     * 判断某天是否是夏季作息时间
     */
    public static boolean isSummer(Date date) {
        int month = Integer.valueOf(DATE_FORMATTER.format(date).substring(5, 7));
        return 5 <= month && month < 10;
    }

    /**
     * 判断时间是否合法
     */
    public static boolean validateTime(Date date) {
        return getNumOfHalfHourDistanceStartTime(date) != -1;
    }

    /**
     * 获取指定时间到开馆时间之间距离了多少个半小时
     * 如果数据不合法（不以整小时或者半小时为单位、在开馆时间之前），返回 -1
     */
    public static int getNumOfHalfHourDistanceStartTime(Date date) {
        String timeStr = TIME_FORMATTER.format(date);
        String[] tmp = timeStr.split(":");
        int hour1 = Integer.valueOf(tmp[0]);
        int minute1 = Integer.valueOf(tmp[1]);
        if (minute1 != 0 && minute1 != 30) {
            return -1;
        }
        int hour2;
        int minute2;
        if (isSummer(date)) {
            tmp = SUMMER_START_TIME.split(":");
            hour2 = Integer.valueOf(tmp[0]);
            minute2 = Integer.valueOf(tmp[1]);
        } else {
            tmp = WINTER_START_TIME.split(":");
            hour2 = Integer.valueOf(tmp[0]);
            minute2 = Integer.valueOf(tmp[1]);
        }
        if (hour1 < hour2) {
            return -1;
        }
        return  (hour1 - hour2) * 2 + (minute1 - minute2) / 30;
    }

}
