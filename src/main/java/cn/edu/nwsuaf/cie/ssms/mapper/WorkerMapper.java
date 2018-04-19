package cn.edu.nwsuaf.cie.ssms.mapper;

import cn.edu.nwsuaf.cie.ssms.model.Worker;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by zhangrenjie on 2018-03-10
 */
@Mapper
public interface WorkerMapper {

    @Insert("insert into worker values (#{uid})")
    int insert(String uid);

    @Delete("delete from worker where uid = #{uid}")
    int delete(String uid);

    @Select("select * from worker")
    List<Worker> getAll();

}
