package cn.edu.nwsuaf.cie.ssms.mapper;

import cn.edu.nwsuaf.cie.ssms.model.Worker;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by zhangrenjie on 2018-03-10
 */
public interface WorkerMapper {

    @Insert("insert into worker values (#{uid}, @{type})")
    public int insert(@Param("uid") String uid, @Param("type") int type);

    @Delete("delete from worker where uid = #{uid} and type = #{type}")
    public int delete(@Param("uid") String uid, @Param("type") int type);

    @Select("select * from worker")
    public List<Worker> getAll();

}
