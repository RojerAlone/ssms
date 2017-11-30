package cn.edu.nwsuaf.cie.ssms.mapper;

import cn.edu.nwsuaf.cie.ssms.model.Order;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKeyWithBLOBs(Order record);

    int updateByPrimaryKey(Order record);
}