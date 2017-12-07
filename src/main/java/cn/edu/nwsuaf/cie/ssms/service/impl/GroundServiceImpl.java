package cn.edu.nwsuaf.cie.ssms.service.impl;

import cn.edu.nwsuaf.cie.ssms.mapper.CloseInfoMapper;
import cn.edu.nwsuaf.cie.ssms.mapper.GroundMapper;
import cn.edu.nwsuaf.cie.ssms.mapper.LongOrderMapper;
import cn.edu.nwsuaf.cie.ssms.mapper.OrderMapper;
import cn.edu.nwsuaf.cie.ssms.model.CloseInfo;
import cn.edu.nwsuaf.cie.ssms.model.Ground;
import cn.edu.nwsuaf.cie.ssms.model.LongOrder;
import cn.edu.nwsuaf.cie.ssms.model.Order;
import cn.edu.nwsuaf.cie.ssms.service.GroundService;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import cn.edu.nwsuaf.cie.ssms.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangrenjie on 2017-12-06
 */
@Service
public class GroundServiceImpl implements GroundService {

    @Autowired
    private GroundMapper groundMapper;
    @Autowired
    private CloseInfoMapper closeInfoMapper;
    @Autowired
    private LongOrderMapper longOrderMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Result getEmptyGround(int type, long startTime, long endTime) {
        List<Ground> grounds;
        if (!TimeUtil.checkTime(startTime, endTime) || (grounds = groundMapper.selectByType(type)).isEmpty()) {
            return Result.errorParam();
        }
        Date startDateTime = new Date(startTime);
        Date endDateTime = new Date(endTime);
        for (Ground ground : grounds) {
            if (!ground.isUsed()) {
                // 查询场地是否被预订
                if (orderMapper.selectNumsBetweenTimeByGroundAndExcludeStat(
                        ground.getId(), startDateTime, endDateTime, Order.STAT_CANCEL) > 0) {
                    ground.setUsed();
                    continue;
                }
                // 如果场地没有被预订，查看该场地是否被关闭
                List<CloseInfo> closeInfos = closeInfoMapper.selectByGidAndStatAndCloseDate(ground.getId(),
                        CloseInfo.STAT_OK, startDateTime);
                if (!closeInfos.isEmpty()) {
                    for (CloseInfo closeInfo : closeInfos) {
                        if (closeInfo.getStartTime() == null
                                || (startDateTime.before(closeInfo.getStartTime()) && endDateTime.after(closeInfo.getStartTime()))
                                || closeInfo.getEndTime() == null
                                || startDateTime.before(closeInfo.getEndTime())) {
                            ground.setUsed();
                            break;
                        }
                    }
                    if (ground.isUsed()) {
                        continue;
                    }
                }
                // 查看是否被长期订单预订
                List<LongOrder> longOrders = longOrderMapper.selectByStatAndDate(LongOrder.STAT_OK, startDateTime);
                for (LongOrder longOrder : longOrders) {
                    // TODO 时间判断
                    if (longOrder.getStartTime() == null) {
                        ground.setUsed();
                        break;
                    }
                }
            }
        }
        return Result.success(grounds);
    }
}
