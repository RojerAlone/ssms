package cn.edu.nwsuaf.cie.ssms.mapper;

import cn.edu.nwsuaf.cie.ssms.model.Longorder;

public interface LongorderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Longorder record);

    int insertSelective(Longorder record);

    Longorder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Longorder record);

    int updateByPrimaryKey(Longorder record);
}