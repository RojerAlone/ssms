package cn.edu.nwsuaf.cie.ssms.service.impl;

import cn.edu.nwsuaf.cie.ssms.config.Price;
import cn.edu.nwsuaf.cie.ssms.mapper.OrderMapper;
import cn.edu.nwsuaf.cie.ssms.model.Order;
import cn.edu.nwsuaf.cie.ssms.model.Result;
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
    private UserHolder userHolder;

    @Override
    public Result order(String uid, int gid, Date startTime, Date endTime) {
        // TODO 判断 gid 是否合法
        if (StringUtils.isEmpty(uid) || startTime.after(endTime)) {
            return Result.error(MsgCenter.ERROR_PARAMS);
        }
        try {
            lock.lock();
            if (orderMapper.selectOrderNumsBetweenTimeByGroundAndStat(gid, startTime, endTime, Order.STAT_PAIED) > 0) {
                return Result.error(MsgCenter.GROUND_ORDERED);
            }
            Order order = new Order();
            order.setGid(gid);
            order.setUid(uid);
            order.setStartTime(startTime);
            order.setEndTime(endTime);
            if (userHolder.getUser().isStudent()) {
                order.setTotal(Price.STUDENT_PRICE * (int)(endTime.getTime() - startTime.getTime()) / 1000 * 60 * 60);
            } else {
                order.setTotal(Price.TEACHER_PRICE * (int)(endTime.getTime() - startTime.getTime()) / 1000 * 60 * 60);
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
        return null;
    }

    @Override
    public Result pay(int orderId) {
        return null;
    }
}
