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
import java.util.Date;
import java.util.List;

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
        if (!TimeUtil.checkTime(startDateTime.getTime(), endDateTime.getTime()) || (grounds = groundMapper.selectByType(type)).isEmpty()) {
            LOGGER.warn("getEmptyGround - error param : type {}, startTime {}, endTime {}", type, startTime, endTime);
            return Result.errorParam();
        }
        for (Ground ground : grounds) {
            if (CommonService.isUsed(ground.getId(), startDateTime, endDateTime)) {
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
            for (int i = start; i <= end; i++) {
                data.add(new int[]{i, order.getGid(), order.getStat()});
            }
        }
        JSONObject json = new JSONObject();
        if (TimeUtil.isSummer(date)){
            json.put("startTime", TimeUtil.SUMMER_START_TIME);
            json.put("endTime", TimeUtil.SUMMER_END_TIME);
        } else {
            json.put("startTime", TimeUtil.WINTER_START_TIME);
            json.put("endTime", TimeUtil.WINTER_END_TIME);
        }
        json.put("data", data);
        return Result.success(json);
    }

    @Override
    public Result getWeekGymnastics() {
        Date endDate = new Date();
        Date startDate = DateUtils.addDays(endDate, -7);
        List<Order> orders = orderMapper.selectGymnasticsByDatesAndStat(Order.STAT_PAIED, startDate, endDate);
        for (Order order : orders) {

        }
        return null;
    }
}
