package cn.edu.nwsuaf.cie.ssms.service.impl;

import cn.edu.nwsuaf.cie.ssms.config.Price;
import cn.edu.nwsuaf.cie.ssms.mapper.GroundMapper;
import cn.edu.nwsuaf.cie.ssms.mapper.OrderMapper;
import cn.edu.nwsuaf.cie.ssms.model.Order;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import cn.edu.nwsuaf.cie.ssms.service.OrderService;
import cn.edu.nwsuaf.cie.ssms.util.MsgCenter;
import cn.edu.nwsuaf.cie.ssms.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
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
    private UserHolder userHolder;

    @Override
    public Result order(String uid, int gid, Date startTime, Date endTime) {
        if (groundMapper.selectByPrimaryKey(gid) == null || StringUtils.isEmpty(uid) || !checkTime(startTime, endTime)) {
            return Result.error(MsgCenter.ERROR_PARAMS);
        }
        try {
            lock.lock();
            if (orderMapper.selectOrderNumsBetweenTimeByGroundAndExcludeStat(gid, startTime, endTime, Order.STAT_PAIED) > 0) {
                return Result.error(MsgCenter.GROUND_ORDERED);
            }
            Order order = new Order();
            order.setGid(gid);
            order.setUid(uid);
            order.setStartTime(startTime);
            order.setEndTime(endTime);
            if (userHolder.getUser().isStudent()) {
                order.setTotal(Price.STUDENT_PRICE * (int)(endTime.getTime() - startTime.getTime()) / (1000 * 60 * 60));
            } else {
                order.setTotal(Price.TEACHER_PRICE * (int)(endTime.getTime() - startTime.getTime()) / (1000 * 60 * 60));
            }
            if (orderMapper.insert(order) == 1) {
                return Result.success(order.getId());
            } else {
                return Result.error(MsgCenter.SERVER_INNER_ERROR);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Result cancel(String uid, int orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null || !order.getUid().equals(uid)) {
            return Result.error(MsgCenter.ERROR_PARAMS);
        }
        order.setStat(Order.STAT_CANCEL);
        if (orderMapper.updateByPrimaryKeySelective(order) == 1) {
            return Result.success();
        } else {
            return Result.error(MsgCenter.SERVER_INNER_ERROR);
        }
    }

    @Override
    public Result pay(int orderId) {
        return null;
    }


    /**
     * 检测时间是否合法（开始时间在结束时间之前同时时间差是整时的）
     */
    private boolean checkTime(Date startTime, Date endTime) {
        return startTime.before(endTime) && ((endTime.getTime() - startTime.getTime()) % (1000 * 60 * 60)) == 0;
    }
}
