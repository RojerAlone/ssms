package cn.edu.nwsuaf.cie.ssms.mapper;

import cn.edu.nwsuaf.cie.ssms.model.Ground;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GroundMapper {
    @Select("select * from ground where id=#{id}")
    Ground selectByPrimaryKey(Integer id);
}