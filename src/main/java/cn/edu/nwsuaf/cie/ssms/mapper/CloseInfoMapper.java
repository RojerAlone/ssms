package cn.edu.nwsuaf.cie.ssms.mapper;

import cn.edu.nwsuaf.cie.ssms.model.CloseInfo;
import cn.edu.nwsuaf.cie.ssms.model.Order;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;
import java.util.List;

@Mapper
public interface CloseInfoMapper {

    @InsertProvider(type = SQLBuilder.class, method = "buildInsert")
    int insert(CloseInfo closeInfo);

    @Update("update closeinfo set stat = #{stat} where id = #{id}")
    int updateStatById(@Param(value = "id") int id, @Param(value = "stat") int stat);

    /**
     * 根据闭馆信息的状态和时间查询
     * @param stat 闭馆信息的状态
     * @param startTime 开始时间，应该是当天零点
     * @return
     */
    @Select("select * from closeinfo where stat = #{stat} and start_time >= #{startTime}")
    List<Order> selectByStatAndTime(@Param(value = "stat") int stat, @Param(value = "startTime") Date startTime);

    class SQLBuilder {
        public String buildInsert(CloseInfo closeInfo) {
            return new SQL(){{
                INSERT_INTO("closeinfo");
                VALUES("type", String.valueOf(closeInfo.getType()));
                VALUES("start_time", closeInfo.getStartTime().toString());
                if (closeInfo.getEndTime() != null) {
                    VALUES("end_time", closeInfo.getEndTime().toString());
                }
                if (closeInfo.getReason() != null) {
                    VALUES("reason", closeInfo.getReason());
                }
            }
            }.toString();
        }
    }

}