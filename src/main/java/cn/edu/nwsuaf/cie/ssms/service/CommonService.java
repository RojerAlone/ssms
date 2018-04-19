package cn.edu.nwsuaf.cie.ssms.service;

import cn.edu.nwsuaf.cie.ssms.mapper.CloseInfoMapper;
import cn.edu.nwsuaf.cie.ssms.mapper.LongOrderMapper;
import cn.edu.nwsuaf.cie.ssms.mapper.OrderMapper;
import cn.edu.nwsuaf.cie.ssms.model.CloseInfo;
import cn.edu.nwsuaf.cie.ssms.model.Order;
import cn.edu.nwsuaf.cie.ssms.model.User;
import cn.edu.nwsuaf.cie.ssms.util.CommonCache;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import cn.edu.nwsuaf.cie.ssms.util.UserAccessUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangrenjie on 2017-12-11
 */
@Service
public class CommonService {

    private static OrderMapper orderMapper;
    private static CloseInfoMapper closeInfoMapper;
    private static LongOrderMapper longOrderMapper;
    private static CommonCache cache;

    @Autowired
    public  void setOrderMapper(OrderMapper orderMapper) {
        CommonService.orderMapper = orderMapper;
    }

    @Autowired
    public void setCloseInfoMapper(CloseInfoMapper closeInfoMapper) {
        CommonService.closeInfoMapper = closeInfoMapper;
    }

    @Autowired
    public void setLongOrderMapper(LongOrderMapper longOrderMapper) {
        CommonService.longOrderMapper = longOrderMapper;
    }

    @Autowired
    public void setCache(CommonCache cache) {
        CommonService.cache = cache;
    }

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
        return false;
    }

    public static Result login(String uid, String token) {
        User user = (User) cache.get(token);
        if (user != null) {
            return Result.innerError();
        }
        user = new User();
        user.setUid(uid);
        user.setAccess(UserAccessUtil.getAccess(uid));
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
