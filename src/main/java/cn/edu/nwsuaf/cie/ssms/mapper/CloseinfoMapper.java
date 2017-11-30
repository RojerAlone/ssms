package cn.edu.nwsuaf.cie.ssms.mapper;

import cn.edu.nwsuaf.cie.ssms.model.Closeinfo;

public interface CloseinfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Closeinfo record);

    int insertSelective(Closeinfo record);

    Closeinfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Closeinfo record);

    int updateByPrimaryKey(Closeinfo record);
}