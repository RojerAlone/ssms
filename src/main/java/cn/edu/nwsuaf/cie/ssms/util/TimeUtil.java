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

    private static final long HALF_HOUR = ONE_HOUR / 2;

    static final long ONE_DAY = ONE_HOUR * 24;

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    private static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm");

    private static final SimpleDateFormat DATETIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private static final Calendar CALENDAR = Calendar.getInstance();

    private static String SUMMER_START_TIME;

    private static String SUMMER_END_TIME;

    private static String WINTER_START_TIME;

    private static String WINTER_END_TIME;

    private static String HOLIDAY_START_TIME;

    private static String HOLIDAY_END_TIME;

    @Value("${properties.time.summer.start}")
    private void setSummerStartTime(String summerStartTime) {
        SUMMER_START_TIME = summerStartTime;
    }

    @Value("${properties.time.summer.end}")
    private void setSummerEndTime(String summerEndTime) {
        SUMMER_END_TIME = summerEndTime;
    }

    @Value("${properties.time.winter.start}")
    private void setWinterStartTime(String winterStartTime) {
        WINTER_START_TIME = winterStartTime;
    }

    @Value("${properties.time.winter.end}")
    private void setWinterEndTime(String winterEndTime) {
        WINTER_END_TIME = winterEndTime;
    }

    @Value("${properties.time.holiday.start}")
    private void setHolidayStartTime(String holidayStartTime) {
        HOLIDAY_START_TIME = holidayStartTime;
    }

    @Value("${properties.time.holiday.end}")
    private void setHolidayEndTime(String holidayEndTime) {
        HOLIDAY_END_TIME = holidayEndTime;
    }

    /**
     * 检测时间是否合法（开始时间在结束时间之前同时时间差是整时的）
     */
    public static boolean checkTime(Date startTime, Date endTime) {
        // 判断是否是以前的日期
        if (startTime.before(new Date())) {
            return false;
        }
        long start = startTime.getTime();
        long end = endTime.getTime();
        // 判断时间间隔是否是半个小时为单位的
        if (start >= end || (end - start) % TimeUtil.HALF_HOUR != 0) {
            return false;
        }
        String[] startTimeStr = formatDateTime(startTime).split(" ");
        String[] endTimeStr = formatDateTime(endTime).split(" ");
        // 判断是否是同一天
        if (!startTimeStr[0].equals(endTimeStr[0])) {
            return false;
        }
        // 判断是否在开馆时间内
        String[] openTimeInfo = getOpenTime(startTime);
        try {
            if (parseTime(startTimeStr[1]).before(parseTime(openTimeInfo[0]))
                    || parseTime(endTimeStr[1]).after(parseTime(openTimeInfo[1]))) {
                return false;
            }
        } catch (ParseException e) {
            LOGGER.error("parse time error", e);
        }
        return true;
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
     */
    public static int getWeekday(Date date) {
        synchronized (CALENDAR) {
            CALENDAR.setTime(date);
            return CALENDAR.get(Calendar.DAY_OF_WEEK);
        }
    }

    /**
     * 获取某天的场馆开放日期，返回一个字符串数组，第一个表示开馆时间，第二个表示闭馆时间
     */
    public static String[] getOpenTime(Date date) {
        // 如果是周末，返回节假日时间
        if (getWeekday(date) > 5) {
            return new String[]{HOLIDAY_START_TIME, HOLIDAY_END_TIME};
        }
        int month = Integer.valueOf(formatDate(date).substring(5, 7));
        if (5 <= month && month < 10) {
            return new String[]{SUMMER_START_TIME, SUMMER_END_TIME};
        }
        return new String[]{WINTER_START_TIME, WINTER_END_TIME};
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
        String timeStr = formatTime(date);
        String[] tmp = timeStr.split(":");
        int hour1 = Integer.valueOf(tmp[0]);
        int minute1 = Integer.valueOf(tmp[1]);
        if (minute1 != 0 && minute1 != 30) {
            return -1;
        }
        tmp = getOpenTime(date)[0].split(":");
        int hour2 = Integer.valueOf(tmp[0]);
        int minute2 = Integer.valueOf(tmp[1]);
        if (hour1 < hour2) {
            return -1;
        }
        return  (hour1 - hour2) * 2 + (minute1 - minute2) / 30;
    }

    /**
     * 比较两个日期之间间隔的天数
     * 不能直接用 getTime / 3600 / 1000 / 24 来比较，这里略去 时分秒 之后再进行比较
     */
    public static int distanceDays(Date date1, Date date2) throws ParseException {
        long time1 = parseDateTime(formatDate(date1)).getTime();
        long time2 = parseDateTime(formatDate(date2)).getTime();
        return (int) ((time2 - time1) / (ONE_HOUR * 24));
    }

}
