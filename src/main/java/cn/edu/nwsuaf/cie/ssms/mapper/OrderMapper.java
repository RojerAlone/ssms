package cn.edu.nwsuaf.cie.ssms.mapper;

import cn.edu.nwsuaf.cie.ssms.model.Order;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface OrderMapper {
    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int selectOrderNumsBetweenTimeByGroundAndStat(@Param(value = "gid") Integer ground,
                                                  @Param(value = "startTime") Date startTime,
                                                  @Param(value = "endTime") Date endTime,
                                                  @Param(value = "stat") Integer stat);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}