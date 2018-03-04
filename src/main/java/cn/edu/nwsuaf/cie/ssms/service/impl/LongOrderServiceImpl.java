package cn.edu.nwsuaf.cie.ssms.service.impl;

import cn.edu.nwsuaf.cie.ssms.mapper.LongOrderMapper;
import cn.edu.nwsuaf.cie.ssms.model.LongOrder;
import cn.edu.nwsuaf.cie.ssms.service.LongOrderService;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhangrenjie on 2017-12-12
 */
@Service
public class LongOrderServiceImpl implements LongOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LongOrderServiceImpl.class);

    @Autowired
    private LongOrderMapper longOrderMapper;

    @Override
    public Result order(LongOrder longOrder) {
        if (longOrderMapper.insert(longOrder) == 1) {
            return Result.success();
        } else {
            return Result.innerError();
        }
    }

    @Override
    public Result delete(int id) {
        LongOrder longOrder = longOrderMapper.selectById(id);
        if (longOrder == null) {
            LOGGER.warn("delete - order don't exist : id {}", id);
            return Result.errorParam();
        }
        if (longOrder.getStat().equals(LongOrder.STAT_DEL) || longOrderMapper.updateStatById(id, LongOrder.STAT_DEL) == 1) {
            return Result.success();
        }
        return Result.innerError();
    }

    @Override
    public Result getAll(int nums) {
        if (nums < 0) {
            LOGGER.warn("getAll - error param : nums {}", nums);
            return Result.errorParam();
        }
        return Result.success(longOrderMapper.selectByStatAndNums(LongOrder.STAT_OK, nums));
    }
}
