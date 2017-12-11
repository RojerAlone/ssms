package cn.edu.nwsuaf.cie.ssms.service.impl;

import cn.edu.nwsuaf.cie.ssms.config.Price;
import cn.edu.nwsuaf.cie.ssms.mapper.CloseInfoMapper;
import cn.edu.nwsuaf.cie.ssms.mapper.GroundMapper;
import cn.edu.nwsuaf.cie.ssms.mapper.LongOrderMapper;
import cn.edu.nwsuaf.cie.ssms.mapper.OrderMapper;
import cn.edu.nwsuaf.cie.ssms.model.CloseInfo;
import cn.edu.nwsuaf.cie.ssms.model.LongOrder;
import cn.edu.nwsuaf.cie.ssms.model.Order;
import cn.edu.nwsuaf.cie.ssms.model.User;
import cn.edu.nwsuaf.cie.ssms.service.CommonService;
import cn.edu.nwsuaf.cie.ssms.service.OrderService;
import cn.edu.nwsuaf.cie.ssms.util.MsgCenter;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import cn.edu.nwsuaf.cie.ssms.util.TimeUtil;
import cn.edu.nwsuaf.cie.ssms.util.UserHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhangrenjie on 2017-11-28
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    private static ReentrantLock lock = new ReentrantLock();

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private GroundMapper groundMapper;
    @Autowired
    private UserHolder userHolder;

    @Override
    public Result getById(int id) {
        Order order = orderMapper.selectByPrimaryKey(id);
        if (order == null || !userHolder.getUser().getUid().equals(order.getUid())) {
            LOGGER.warn("getById - error param : order {} uid {}", order, userHolder.getUser().getUid());
            return Result.errorParam();
        }
        return Result.success(order);
    }

    @Override
    public Result getMyOrders(int nums) {
        if (nums <= 0) {
            LOGGER.warn("getMyOrders - error param : nums {}", nums);
            return Result.errorParam();
        }
        return Result.success(orderMapper.selectByUid(userHolder.getUser().getUid(), nums));
    }

    @Override
    public Result getNotPaiedOrders(int nums) {
        if (nums <= 0) {
            LOGGER.warn("getNotPaiedOrders - error param : nums {}", nums);
            return Result.errorParam();
        }
        return Result.success(orderMapper.selectByUidAndStat(userHolder.getUser().getUid(), Order.STAT_NOT_PAY, nums));
    }

    @Override
    public Result getPaiedOrders(int nums) {
        if (nums <= 0) {
            LOGGER.warn("getPaiedOrders - error param : nums {}", nums);
            return Result.errorParam();
        }
        return Result.success(orderMapper.selectByUidAndStat(userHolder.getUser().getUid(), Order.STAT_PAIED, nums));
    }

    @Override
    public Result getCanceledOrders(int nums) {
        if (nums <= 0) {
            LOGGER.warn("getCanceledOrders - error param : nums {}", nums);
            return Result.errorParam();
        }
        return Result.success(orderMapper.selectByUidAndStat(userHolder.getUser().getUid(), Order.STAT_CANCEL, nums));
    }

    @Override
    public Result order(int gid, long startTime, long endTime) {
        if (groundMapper.selectByPrimaryKey(gid) == null || !TimeUtil.checkTime(startTime, endTime)) {
            LOGGER.warn("order - error param : gid {}, startTime {}, endTime {}", gid, startTime, endTime);
            return Result.errorParam();
        }
        try {
            lock.lock();
            Date startDateTime = new Date(startTime);
            Date endDateTime = new Date(endTime);
            if (orderMapper.selectNumsBetweenTimeByGroundAndExcludeStat(gid, startDateTime, endDateTime, Order.STAT_CANCEL) > 0
                    || CommonService.isUsed(gid, startDateTime, endDateTime)) {
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
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null || order.getStat().equals(Order.STAT_CANCEL)) {
            return Result.errorParam();
        }
        if (!userHolder.getUser().getUid().equals(order.getUid())) {
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
