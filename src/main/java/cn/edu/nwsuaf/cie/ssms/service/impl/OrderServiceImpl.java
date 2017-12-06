package cn.edu.nwsuaf.cie.ssms.service.impl;

import cn.edu.nwsuaf.cie.ssms.config.Price;
import cn.edu.nwsuaf.cie.ssms.mapper.CloseInfoMapper;
import cn.edu.nwsuaf.cie.ssms.mapper.GroundMapper;
import cn.edu.nwsuaf.cie.ssms.mapper.LongOrderMapper;
import cn.edu.nwsuaf.cie.ssms.mapper.OrderMapper;
import cn.edu.nwsuaf.cie.ssms.model.Order;
import cn.edu.nwsuaf.cie.ssms.model.User;
import cn.edu.nwsuaf.cie.ssms.service.OrderService;
import cn.edu.nwsuaf.cie.ssms.util.MsgCenter;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import cn.edu.nwsuaf.cie.ssms.util.TimeUtil;
import cn.edu.nwsuaf.cie.ssms.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhangrenjie on 2017-11-28
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static ReentrantLock lock = new ReentrantLock();

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private GroundMapper groundMapper;
    @Autowired
    private CloseInfoMapper closeInfoMapper;
    @Autowired
    private LongOrderMapper longOrderMapper;
    @Autowired
    private UserHolder userHolder;

    @Override
    public Result order(int gid, long startTime, long endTime) {
        if (userHolder.getUser() == null) {
            return Result.error(MsgCenter.ERROR_AUTH);
        }
        if (groundMapper.selectByPrimaryKey(gid) == null || !TimeUtil.checkTime(startTime, endTime)) {
            return Result.error(MsgCenter.ERROR_PARAMS);
        }
        try {
            lock.lock();
            if (orderMapper.selectNumsBetweenTimeByGroundAndExcludeStat(gid, new Date(startTime), new Date(endTime), Order.STAT_CANCEL) > 0) {
                return Result.error(MsgCenter.GROUND_ORDERED);
            }
            Order order = new Order();
            order.setGid(gid);
            order.setUid(userHolder.getUser().getUid());
            order.setStartTime(new Date(startTime));
            order.setEndTime(new Date(endTime));
            if (userHolder.getUser().isStudent()) {
                order.setTotal((int) (Price.STUDENT_PRICE * (endTime - startTime) / TimeUtil.ONE_HOUR));
            } else {
                order.setTotal((int) (Price.TEACHER_PRICE * (endTime - startTime) / TimeUtil.ONE_HOUR));
            }
            if (orderMapper.insert(order) == 1) {
                return Result.success(order.getId());
            } else {
                return Result.innerError();
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Result cancel(int orderId) {
        User user = userHolder.getUser();
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null || order.getStat().equals(Order.STAT_CANCEL)) {
            return Result.error(MsgCenter.ERROR_PARAMS);
        }
        if (!user.getUid().equals(order.getUid())) {
            return Result.error(MsgCenter.ERROR_AUTH);
        }
        if (order.getStartTime().getTime() - System.currentTimeMillis() < TimeUtil.ONE_DAY) {
            return Result.error(MsgCenter.ORDER_CANCEL_FAILED);
        }
        if (orderMapper.updateStatById(orderId, Order.STAT_CANCEL) == 1) {
            return Result.success();
        } else {
            return Result.innerError();
        }
    }

    @Override
    public Result pay(int orderId) {
        return null;
    }

}
