package cn.edu.nwsuaf.cie.ssms.service;

import cn.edu.nwsuaf.cie.ssms.util.Result;

import java.util.Date;

/**
 * Created by zhangrenjie on 2017-12-06
 */
public interface GroundService {

    Result getEmptyGround(int type, String startTime, String endTime);

    Result getBadmintonInfo(String dateStr);

    Result getGymnasticsInfo();

}
