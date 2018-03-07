package cn.edu.nwsuaf.cie.ssms.service;

import cn.edu.nwsuaf.cie.ssms.mapper.CloseInfoMapper;
import cn.edu.nwsuaf.cie.ssms.mapper.LongOrderMapper;
import cn.edu.nwsuaf.cie.ssms.mapper.OrderMapper;
import cn.edu.nwsuaf.cie.ssms.model.CloseInfo;
import cn.edu.nwsuaf.cie.ssms.model.LongOrder;
import cn.edu.nwsuaf.cie.ssms.model.Order;
import cn.edu.nwsuaf.cie.ssms.model.User;
import cn.edu.nwsuaf.cie.ssms.util.CommonCache;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import cn.edu.nwsuaf.cie.ssms.util.TimeUtil;
import cn.edu.nwsuaf.cie.ssms.util.UserCheck;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangrenjie on 2017-12-11
 */
public class CommonService {

    @Autowired
    private static OrderMapper orderMapper;
    @Autowired
    private static CloseInfoMapper closeInfoMapper;
    @Autowired
    private static LongOrderMapper longOrderMapper;
    @Autowired
    private static CommonCache cache;

    /**
     * 检测某场地某个时间段内是否可用
     */
    public static boolean isUsed(int gid, Date startDateTime, Date endDateTime) {
        // 查询场地是否被预订
        if (orderMapper.selectNumsBetweenTimeByGroundAndExcludeStat(
                gid, startDateTime, endDateTime, Order.STAT_CANCEL) > 0) {
            return true;
        }
        // 如果场地没有被预订，查看该场地是否被关闭
        List<CloseInfo> closeInfos = closeInfoMapper.selectByGidAndStatAndCloseDate(gid, CloseInfo.STAT_OK, startDateTime);
        if (!closeInfos.isEmpty()) {
            for (CloseInfo closeInfo : closeInfos) {
                if (closeInfo.getStartTime() == null
                        || (startDateTime.before(closeInfo.getStartTime()) && endDateTime.after(closeInfo.getStartTime()))
                        || closeInfo.getEndTime() == null
                        || startDateTime.before(closeInfo.getEndTime())) {
                    return true;
                }
            }
        }
        // 查看是否被长期订单预订
        List<LongOrder> longOrders = longOrderMapper.selectByGidAndStatAndDate(gid, LongOrder.STAT_OK, startDateTime);
        for (LongOrder longOrder : longOrders) {
            if (longOrder.getWeekday() != TimeUtil.getWeekday(startDateTime)) {
                continue;
            }
            if (longOrder.getStartTime() == null) { // 整天都不开放
                return true;
            } else {
                if (longOrder.getEndTime() == null) { // 如果长期订单开始时间从开始到当天结束
                    if (TimeUtil.compareTime(endDateTime, longOrder.getStartTime()) == 1) { // 如果查询的结束时间大于长订单开始时间
                        return true;
                    }
                } else { // 如果开始时间和结束时间都不为空
                    if (!(TimeUtil.compareTime(startDateTime, longOrder.getEndTime()) != -1
                            || TimeUtil.compareTime(endDateTime, longOrder.getStartTime()) != 1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static Result login(String uid, String token) {
        User user = (User) cache.get(token);
        if (user != null) {
            return Result.innerError();
        }
        user = new User();
        user.setUid(uid);
        user.setAccess(UserCheck.getAccess(uid));
        cache.put(token, user);
        return Result.success(token);
    }

    public static Result logout(String uid, String token) {
        User user = (User) cache.get(token);
        if (user == null || !user.getUid().equalsIgnoreCase(uid)) {
            return Result.errorParam();
        }
        cache.remove(token);
        return Result.success();
    }
}
