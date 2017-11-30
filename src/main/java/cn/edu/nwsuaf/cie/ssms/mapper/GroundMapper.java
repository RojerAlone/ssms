package cn.edu.nwsuaf.cie.ssms.mapper;

import cn.edu.nwsuaf.cie.ssms.model.Ground;

public interface GroundMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Ground record);

    int insertSelective(Ground record);

    Ground selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ground record);

    int updateByPrimaryKey(Ground record);
}