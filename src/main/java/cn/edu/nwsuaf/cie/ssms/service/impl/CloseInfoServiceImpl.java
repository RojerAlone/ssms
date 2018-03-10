package cn.edu.nwsuaf.cie.ssms.service.impl;

import cn.edu.nwsuaf.cie.ssms.mapper.CloseInfoMapper;
import cn.edu.nwsuaf.cie.ssms.model.CloseInfo;
import cn.edu.nwsuaf.cie.ssms.service.CloseInfoService;
import cn.edu.nwsuaf.cie.ssms.util.MsgCenter;
import cn.edu.nwsuaf.cie.ssms.util.PageUtil;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import cn.edu.nwsuaf.cie.ssms.util.UserHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by RojerAlone on 2017-12-05.
 */
@Service
public class CloseInfoServiceImpl implements CloseInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CloseInfoServiceImpl.class);

    @Autowired
    private CloseInfoMapper closeInfoMapper;

    @Autowired
    private UserHolder userHolder;

    @Override
    public Result close(CloseInfo closeInfo) {
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
        return Result.success(closeInfoMapper.selectByStatAndNums(CloseInfo.STAT_OK, pageInfo[0], pageInfo[1]));
    }
}
