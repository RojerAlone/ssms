package cn.edu.nwsuaf.cie.ssms.service.impl;

import cn.edu.nwsuaf.cie.ssms.mapper.CloseInfoMapper;
import cn.edu.nwsuaf.cie.ssms.mapper.GroundMapper;
import cn.edu.nwsuaf.cie.ssms.mapper.OrderMapper;
import cn.edu.nwsuaf.cie.ssms.model.Ground;
import cn.edu.nwsuaf.cie.ssms.model.Order;
import cn.edu.nwsuaf.cie.ssms.service.CommonService;
import cn.edu.nwsuaf.cie.ssms.service.GroundService;
import cn.edu.nwsuaf.cie.ssms.util.MsgCenter;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import cn.edu.nwsuaf.cie.ssms.util.TimeUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

/**
 * Created by zhangrenjie on 2017-12-06
 */
@Service
public class GroundServiceImpl implements GroundService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroundServiceImpl.class);

    @Autowired
    private GroundMapper groundMapper;
    @Autowired
    private CloseInfoMapper closeInfoMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Result getEmptyGround(int type, String startTime, String endTime) {
        List<Ground> grounds;
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
        if ((grounds = groundMapper.selectByType(type)).isEmpty()) {
            LOGGER.warn("getEmptyGround - type {}", type);
            return Result.errorParam();
        }
        for (Ground ground : grounds) {
            if (CommonService.isUsed(ground.getId(), startDateTime, endDateTime) != null) {
                ground.setUsed();
            }
        }
        return Result.success(grounds);
    }

    @Override
    public Result getBadmintonInfo(String dateStr) {
        Date date = new Date();
        if (dateStr != null) {
            try {
                date = TimeUtil.parseDateTime(dateStr);
            } catch (ParseException e) {
                LOGGER.error("error time format", e);
                return Result.error(String.format(MsgCenter.ERROR_TIME_FORMAT, e.getMessage()));
            }
        }
        List<Order> orders = orderMapper.selectBadmintonByDateAndExcludeStat(Order.STAT_CANCEL, date);
        JSONArray data = new JSONArray();
        for (Order order : orders) {
            int start = TimeUtil.getNumOfHalfHourDistanceStartTime(order.getStartTime());
            int end = TimeUtil.getNumOfHalfHourDistanceStartTime(order.getEndTime());
            for (int i = start; i < end; i++) {
                /**
                 * 前端使用 echarts 显示某天的预订情况，返回一个 array(time, gid, stat)
                 * 渲染的时候 time 是横坐标，gid 是纵坐标，stat 是状态，不同的状态显示不同的颜色
                 */
                data.add(new int[]{i, order.getGid(), order.getStat()});
            }
        }
        JSONObject json = new JSONObject();
        String[] openTime = TimeUtil.getOpenTime(date);
        String startTime = openTime[0];
        String endTime = openTime[1];
        json.put("startTime", startTime);
        json.put("endTime", endTime);
        json.put("data", data);
        return Result.success(json);
    }

    @Override
    public Result getGymnasticsInfo() {
        int countDay = 7;
        Date startDate = new Date();
        Date endDate = DateUtils.addDays(startDate, countDay - 1);
        List<Order> orders = orderMapper.selectGymnasticsByDatesAndStat(Order.STAT_PAIED, startDate, endDate);
        Set<String> orderSet = new HashSet<>(orders.size());
        for (Order order : orders) {
            int time = TimeUtil.formatTime(order.getStartTime()).equals(Ground.GYMNASTICS_REST_TIME) ? 0 : 1;
            orderSet.add(TimeUtil.formatDate(order.getStartTime()) + "_" + time);
        }
        JSONArray arrayData = new JSONArray(countDay);
        for (int day = 0; day < countDay; day++) {
            JSONObject data = new JSONObject();
            String date = TimeUtil.formatDate(DateUtils.addDays(startDate, day));
            data.put("date", date);
            JSONObject json0 = new JSONObject(1);
            json0.put("used", orderSet.contains(date + "_0"));
            JSONObject json1 = new JSONObject(1);
            json1.put("used", orderSet.contains(date + "_1"));
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(json0);
            jsonArray.add(json1);
            data.put("data", jsonArray);
            arrayData.add(data);
        }
        return Result.success(arrayData);
    }
}
