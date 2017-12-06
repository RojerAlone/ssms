package cn.edu.nwsuaf.cie.ssms.service.impl;

import cn.edu.nwsuaf.cie.ssms.mapper.CloseInfoMapper;
import cn.edu.nwsuaf.cie.ssms.mapper.GroundMapper;
import cn.edu.nwsuaf.cie.ssms.mapper.LongOrderMapper;
import cn.edu.nwsuaf.cie.ssms.model.CloseInfo;
import cn.edu.nwsuaf.cie.ssms.model.Ground;
import cn.edu.nwsuaf.cie.ssms.model.LongOrder;
import cn.edu.nwsuaf.cie.ssms.service.GroundService;
import cn.edu.nwsuaf.cie.ssms.util.MsgCenter;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangrenjie on 2017-12-06
 */
@Service
public class GroundServiceImpl implements GroundService {

    @Autowired
    private GroundMapper groundMapper;
    @Autowired
    private LongOrderMapper longOrderMapper;
    @Autowired
    private CloseInfoMapper closeInfoMapper;

    @Override
    public Result getEmptyGround(int type) {
        List<Ground> grounds = groundMapper.selectByType(type);
        if (grounds.isEmpty()) {
            return Result.error(MsgCenter.ERROR_PARAMS);
        }

        return Result.success(grounds);
    }
}
