package cn.edu.nwsuaf.cie.ssms.service.impl;

import cn.edu.nwsuaf.cie.ssms.config.Price;
import cn.edu.nwsuaf.cie.ssms.mapper.GroundMapper;
import cn.edu.nwsuaf.cie.ssms.mapper.OrderMapper;
import cn.edu.nwsuaf.cie.ssms.model.Access;
import cn.edu.nwsuaf.cie.ssms.model.Order;
import cn.edu.nwsuaf.cie.ssms.service.CommonService;
import cn.edu.nwsuaf.cie.ssms.service.OrderService;
import cn.edu.nwsuaf.cie.ssms.util.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhangrenjie on 2017-11-28
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    /**
     * 统计数据时候默认统计天数
     */
    private static final int COUNT_DAYS = 7;

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
    public Result getMyOrders(int page, int nums) {
        if (nums <= 0) {
            LOGGER.warn("getMyOrders - error param : nums {}", nums);
            return Result.errorParam();
        }
        return Result.success(orderMapper.selectByUid(userHolder.getUser().getUid(), nums));
    }

    @Override
    public Result getNotPaiedOrders(int page, int nums) {
        int[] pageInfo = PageUtil.getPage(page, nums);
        return Result.success(orderMapper.selectByUidAndStat(userHolder.getUser().getUid(), Order.STAT_NOT_PAY,
                pageInfo[0], pageInfo[1]));
    }

    @Override
    public Result getPaiedOrders(int page, int nums) {
        int[] pageInfo = PageUtil.getPage(page, nums);
        return Result.success(orderMapper.selectByUidAndStat(userHolder.getUser().getUid(), Order.STAT_PAIED,
                pageInfo[0], pageInfo[1]));
    }

    @Override
    public Result getCanceledOrders(int page, int nums) {
        int[] pageInfo = PageUtil.getPage(page, nums);
        return Result.success(orderMapper.selectByUidAndStat(userHolder.getUser().getUid(), Order.STAT_CANCEL,
                pageInfo[0], pageInfo[1]));
    }

    @Override
    public Result order(int gid, String startTime, String endTime) {
        Date startDateTime = null;
        Date endDateTime = null;
        try {
            startDateTime = TimeUtil.parseDateTime(startTime);
            endDateTime = TimeUtil.parseDateTime(endTime);
        } catch (ParseException e) {
            LOGGER.error("error time format", e);
            return Result.error(String.format(MsgCenter.ERROR_TIME_FORMAT, e.getMessage()));
        }
        if (groundMapper.selectByPrimaryKey(gid) == null || !TimeUtil.checkTime(startDateTime.getTime(), endDateTime.getTime())) {
            LOGGER.warn("order - error param : gid {}, startTime {}, endTime {}", gid, startTime, endTime);
            return Result.errorParam();
        }
        try {
            lock.lock();
            if (orderMapper.selectNumsBetweenTimeByGroundAndExcludeStat(gid, startDateTime, endDateTime, Order.STAT_CANCEL) > 0
                    || CommonService.isUsed(gid, startDateTime, endDateTime)) {
                return Result.error(MsgCenter.GROUND_ORDERED);
            }
            Order order = new Order();
            order.setGid(gid);
            order.setUid(userHolder.getUser().getUid());
            order.setStartTime(startDateTime);
            order.setEndTime(endDateTime);
            int times = (int) ((endDateTime.getTime() - startDateTime.getTime()) / TimeUtil.ONE_HOUR);
            if (userHolder.getUser().isStudent()) {
                order.setTotal(Price.STUDENT_PRICE * times);
            } else {
                order.setTotal(Price.TEACHER_PRICE * times);
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

    /**
     * 直接预订场地，使用了 order 和 pay 方法，有很小的几率存在问题（这个事务没有执行成功），暂不考虑
     */
    @Override
    public Result orderAndPay(int gid, String startTime, String endTime) {
        Result result = order(gid, startTime, endTime);
        if (!result.isSuccess()) {
            return result;
        }
        return pay((Integer) result.getResult());
    }

    @Override
    public Result cancel(int orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null || order.getStat().equals(Order.STAT_CANCEL)) {
            return Result.errorParam();
        }
        // 如果是普通用户，不能取消不是自己的订单
        if (userHolder.getUser().getAccess() == Access.NORMAL && !userHolder.getUser().getUid().equals(order.getUid())) {
            LOGGER.warn("cancel - uid not match order : uid {}, order {}", userHolder.getUser().getUid(), order);
            return Result.error(MsgCenter.ERROR_AUTH);
        }
        if (orderMapper.updateStatById(orderId, Order.STAT_CANCEL) == 1) {
            return Result.success();
        } else {
            return Result.innerError();
        }
    }

    @Override
    public Result pay(int orderId) {
        int res = orderMapper.updateStatById(orderId, Order.STAT_PAIED);
        if (res != 1) {
            LOGGER.error("order not exist: {}", orderId);
            return Result.error(MsgCenter.ORDER_NOT_EXIST);
        }
        return Result.success();
    }

    @Override
    public Result getSportTime() {
        List<Order> orders = getPersonalOrder();
        if (orders.isEmpty()) {
            return Result.success();
        }
        Map<String, Long> costInfo = new HashMap<>();
        for (Order order : orders) {
            String typeName = GroundUtil.getGroundTypeNameByGid(order.getGid());
            long times = order.getEndTime().getTime() - order.getStartTime().getTime();
            costInfo.put(typeName, costInfo.getOrDefault(typeName, 0L) + times);
        }
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<String, Long> entry : costInfo.entrySet()) {
            JSONObject json = new JSONObject();
            json.put("type", entry.getKey());
            json.put("value", entry.getValue() / TimeUtil.ONE_HOUR);
            jsonArray.add(json);
        }
        return Result.success(jsonArray);
    }

    /**
     * 获取用户最近 COUNT_DAY 的订单
     */
    private List<Order> getPersonalOrder() {
        String uid = userHolder.getUser().getUid();
        int stat = Order.STAT_PAIED;
        Date endDate = new Date();
        Date startDate = DateUtils.addDays(endDate, -COUNT_DAYS);
        return orderMapper.selectByStatAndUidAndTime(uid, stat, startDate, endDate);
    }

}
