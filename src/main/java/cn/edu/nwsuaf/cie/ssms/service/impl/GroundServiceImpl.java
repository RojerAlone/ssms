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
        if (!TimeUtil.checkTime(startDateTime, endDateTime)) {
            return Result.error(MsgCenter.ERROR_TIME);
        }
        if ((grounds = groundMapper.selectByType(type)).isEmpty()) {
            LOGGER.warn("getEmptyGround - type {}", type);
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
//        String[] openTime = new String[]{"15:00", "22:00"};
        String startTime = openTime[0];
        String endTime = openTime[1];
        json.put("startTime", startTime);
        json.put("endTime", endTime);
        json.put("data", data);
        return Result.success(json);
    }

    @Override
    public Result getGymnasticsInfo() {
        Date startDate = new Date();
        Date endDate = DateUtils.addDays(startDate, 12);
        List<Order> orders = orderMapper.selectGymnasticsByDatesAndStat(Order.STAT_PAIED, startDate, endDate);
        JSONArray data = new JSONArray();
        for (Order order : orders) {
            try {
                int start = TimeUtil.getNumOfHalfHourDistanceStartTime(order.getStartTime());
                int end = TimeUtil.getNumOfHalfHourDistanceStartTime(order.getEndTime());
                for (int i = start; i < end; i++) {
                    int distanceDays = TimeUtil.distanceDays(order.getStartTime(), startDate);
                    data.add(new int[]{i, distanceDays, order.getStat()});
                }
            } catch (ParseException e) {
                LOGGER.error("time parse error", e);
                return Result.error(String.format(MsgCenter.ERROR_TIME_FORMAT, e.getMessage()));
            }
        }
        JSONObject json = new JSONObject();
        json.put("data", data);
        // TODO 没有确定体操室的开始和结束时间
        json.put("startTime", "10:00");
        json.put("endTime", "22:00");
        return Result.success(json);
    }
}
