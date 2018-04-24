package cn.edu.nwsuaf.cie.ssms.service.impl;

import cn.edu.nwsuaf.cie.ssms.config.Price;
import cn.edu.nwsuaf.cie.ssms.mapper.GroundMapper;
import cn.edu.nwsuaf.cie.ssms.mapper.OrderMapper;
import cn.edu.nwsuaf.cie.ssms.model.Access;
import cn.edu.nwsuaf.cie.ssms.model.Ground;
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
import org.springframework.scheduling.annotation.Scheduled;
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
        return orderWithStat(gid, startTime, endTime, Order.STAT_NOT_PAY);
    }

    @Override
    public Result orderAndPay(int gid, String startTime, String endTime) {
        return orderWithStat(gid, startTime, endTime, Order.STAT_PAIED);
    }

    @Override
    public Result orderGymnastics(String date, int time) {
        Date tmpDate;
        try {
            tmpDate = TimeUtil.parseDate(date);
            if (TimeUtil.distanceDays(new Date(), tmpDate) < 0) {
                return Result.error(MsgCenter.ERROR_TIME);
            }
        } catch (ParseException e) {
            LOGGER.error("parse date error", e);
            return Result.error(String.format(MsgCenter.ERROR_TIME_FORMAT, date));
        }
        Order order = new Order();
        order.setUid(userHolder.getUser().getUid());
        order.setGid(Ground.GYMNASTICS_ID);
        if (time == 0) {
            date += " " + Ground.GYMNASTICS_REST_TIME;
        } else {
            date += " " + Ground.GYMNASTICS_NIGHT_TIME;
        }
        try {
            tmpDate = TimeUtil.parseDateTime(date);
        } catch (ParseException e) {
            LOGGER.error("orderGymnastics - inner error", e);
            return Result.innerError();
        }
        if (CommonService.isUsed(Ground.GYMNASTICS_ID, tmpDate, tmpDate)) {
            return Result.error(MsgCenter.GROUND_ORDERED);
        }
        order.setStartTime(tmpDate);
        order.setEndTime(tmpDate);
        order.setStat(Order.STAT_PAIED);
        order.setTotal(0);
        int res = orderMapper.insertWithStat(order);
        if (res == 0) {
            return Result.innerError();
        }
        return Result.success(order.getId());
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
    public Result getPersonalSportTime() {
        String uid = userHolder.getUser().getUid();
        Date endDate = new Date();
        Date startDate = DateUtils.addDays(endDate, -COUNT_DAYS);
        List<Order> orders = orderMapper.selectByStatAndUidAndTime(uid, Order.STAT_PAIED, startDate, endDate);
        return Result.success(parseSportTimeInfo(orders));
    }

    @Override
    public Result getAllSportTime() {
        Date endDate = new Date();
        Date startDate = DateUtils.addDays(endDate, -COUNT_DAYS);
        List<Order> orders = orderMapper.selectByStatAndDates(Order.STAT_PAIED, startDate, endDate);
        return Result.success(parseSportTimeInfo(orders));
    }

    private JSONArray parseSportTimeInfo(List<Order> orders) {
        // 格式为 日期-时长
        Map<String, Long> sportTime = new HashMap<>(COUNT_DAYS);
        for (Order order : orders) {
            String typeName = GroundUtil.getGroundTypeNameByGid(order.getGid());
            long times = order.getEndTime().getTime() - order.getStartTime().getTime();
            sportTime.put(TimeUtil.formatDate(order.getStartTime()), sportTime.getOrDefault(typeName, 0L) + times);
        }
        JSONArray jsonArray = new JSONArray();
        Date now = new Date();
        for (int day = 0; day < COUNT_DAYS; day++) {
            JSONObject json = new JSONObject();
            String date = TimeUtil.formatDate(DateUtils.addDays(now, -day));
            json.put("date", date);
            json.put("value", sportTime.getOrDefault(date, 0L) / TimeUtil.ONE_HOUR);
            jsonArray.add(json);
        }
        return jsonArray;
    }

    private Result orderWithStat(int gid, String startTime, String endTime, int stat) {
        Date startDateTime = null;
        Date endDateTime = null;
        try {
            startDateTime = TimeUtil.parseDateTime(startTime);
            endDateTime = TimeUtil.parseDateTime(endTime);
        } catch (ParseException e) {
            LOGGER.error("error time format", e);
            return Result.error(String.format(MsgCenter.ERROR_TIME_FORMAT, e.getMessage()));
        }
        if (!TimeUtil.checkTime(startDateTime, endDateTime)) {
            return Result.error(MsgCenter.ERROR_TIME);
        }
        if (groundMapper.selectByPrimaryKey(gid) == null) {
            LOGGER.warn("order - error param : gid {}", gid);
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
            order.setStat(stat);
            int times = (int) ((endDateTime.getTime() - startDateTime.getTime()) / TimeUtil.ONE_HOUR);
            if (userHolder.getUser().isStudent()) {
                order.setTotal(Price.STUDENT_PRICE * times);
            } else {
                order.setTotal(Price.TEACHER_PRICE * times);
            }
            order.setStartTime(startDateTime);
            order.setEndTime(endDateTime);
            if (orderMapper.insert(order) == 1) {
                return Result.success(order.getId());
            } else {
                return Result.innerError();
            }
        } finally {
            lock.unlock();
        }
    }

    @Scheduled(fixedRate = 1000 * 60)
    private void cleanNotPaiedOrders() {
        Date date = DateUtils.addDays(new Date(), -1);
        orderMapper.updateStatByStatAndDate(Order.STAT_CANCEL, Order.STAT_NOT_PAY, date);
    }

}
