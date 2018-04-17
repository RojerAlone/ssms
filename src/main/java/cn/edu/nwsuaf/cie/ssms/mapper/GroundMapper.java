package cn.edu.nwsuaf.cie.ssms.mapper;

import cn.edu.nwsuaf.cie.ssms.model.Ground;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GroundMapper {
    @Select("select * from ground where id=#{id}")
    Ground selectByPrimaryKey(Integer id);

    @Select("select * from ground where type = #{type}")
    List<Ground> selectByType(int type);

    @Select("select * from ground")
    List<Ground> selectAllInfo();

}