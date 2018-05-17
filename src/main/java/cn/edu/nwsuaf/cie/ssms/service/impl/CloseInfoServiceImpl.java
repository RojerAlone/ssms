package cn.edu.nwsuaf.cie.ssms.service.impl;

import cn.edu.nwsuaf.cie.ssms.mapper.CloseInfoMapper;
import cn.edu.nwsuaf.cie.ssms.model.CloseInfo;
import cn.edu.nwsuaf.cie.ssms.service.CloseInfoService;
import cn.edu.nwsuaf.cie.ssms.util.*;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

/**
 * Created by RojerAlone on 2017-12-05.
 */
@Service
public class CloseInfoServiceImpl implements CloseInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CloseInfoServiceImpl.class);

    @Autowired
    private CloseInfoMapper closeInfoMapper;

    @Override
    public Result close(String startDate, String startTime, String endDate, String endTime, String reason) {
        CloseInfo closeInfo = new CloseInfo();
        try {
            closeInfo.setStartDate(TimeUtil.parseDate(startDate));
            closeInfo.setEndDate(TimeUtil.parseDate(endDate));
            if (startTime != null && !startTime.isEmpty()) {
                closeInfo.setStartTime(TimeUtil.parseTime(startTime));
            }
            if (endTime != null && !startTime.isEmpty()) {
                closeInfo.setEndTime(TimeUtil.parseTime(endTime));
            }
        } catch (ParseException e) {
            LOGGER.error("add close parse time error", e);
            return Result.error(String.format(MsgCenter.ERROR_TIME_FORMAT, e.getMessage()));
        }
        closeInfo.setReason(reason);
        if (closeInfoMapper.insert(closeInfo) == 1) {
            return Result.success();
        } else {
            return Result.error(MsgCenter.SERVER_INNER_ERROR);
        }
    }

    @Override
    public Result delete(int id) {
        if (closeInfoMapper.updateStatById(id, CloseInfo.STAT_DEL) == 1) {
            return Result.success();
        } else {
            return Result.error(MsgCenter.SERVER_INNER_ERROR);
        }
    }

    @Override
    public Result getAll(int page, int nums) {
        int[] pageInfo = PageUtil.getPage(page, nums);
        int stat = CloseInfo.STAT_OK;
        List<CloseInfo> infos = closeInfoMapper.selectByStatAndNums(stat, pageInfo[0], pageInfo[1]);
        int total = closeInfoMapper.selectCountByStat(stat);
        JSONObject json = new JSONObject();
        json.put("total", total);
        json.put("data", infos);
        return Result.success(json);
    }
}
