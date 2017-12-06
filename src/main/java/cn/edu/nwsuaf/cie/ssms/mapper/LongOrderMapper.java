package cn.edu.nwsuaf.cie.ssms.mapper;

import cn.edu.nwsuaf.cie.ssms.model.LongOrder;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;
import java.util.List;

@Mapper
public interface LongOrderMapper {

    @InsertProvider(type = SQLBuilder.class, method = "buildInsert")
    int insert(LongOrder order);

    @Select("select * from longorder where stat = #{stat}")
    List<LongOrder> selectByStat(int stat);

    @Select("select * from longorder where stat = #{stat} and #{date} between start_date and end_date")
    List<LongOrder> selectByStatAndDate(@Param("stat") int stat, @Param("date") Date date);

    @Update("update longorder set stat = #{stat} where id = #{id}")
    int updateStatById(@Param(value = "id") int id, @Param(value = "stat") int stat);

    class SQLBuilder {
        public String buildInsert(final LongOrder order) {
            return new SQL(){
                {
                    INSERT_INTO("longorder");
                    VALUES("gid", String.valueOf(order.getGid()));
                    VALUES("start_date", order.getStartDate().toString());
                    VALUES("end_date", order.getEndDate().toString());
                    VALUES("startTime", order.getStartTime().toString());
                    if (order.getEndTime() != null) {
                        VALUES("endTime", order.getEndTime().toString());
                    }
                    VALUES("weekday", String.valueOf(order.getWeekday()));
                }
            }.toString();
        }
    }

}