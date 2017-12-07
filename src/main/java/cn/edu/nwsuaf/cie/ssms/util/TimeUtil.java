package cn.edu.nwsuaf.cie.ssms.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangrenjie on 2017-12-04
 */
public class TimeUtil {

    public static final long ONE_HOUR = 1000 * 60 * 60;

    public static final long ONE_DAY = ONE_HOUR * 24;

    /**
     * 检测时间是否合法（开始时间在结束时间之前同时时间差是整时的）
     */
    public static boolean checkTime(long startTime, long endTime) {
        return startTime < endTime && (endTime - startTime) % TimeUtil.ONE_HOUR == 0;
    }

    private static SimpleDateFormat SDF_WITHOUT_TIME = new SimpleDateFormat("yy-MM-dd");

    public static Date DateWithoutTime(long time) {
        return null;
    }

}
