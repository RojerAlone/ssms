package cn.edu.nwsuaf.cie.ssms.mapper;

import cn.edu.nwsuaf.cie.ssms.model.LongOrder;
import cn.edu.nwsuaf.cie.ssms.util.TimeUtil;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;
import java.util.List;

@Mapper
public interface LongOrderMapper {

    @InsertProvider(type = SQLBuilder.class, method = "insert")
    int insert(LongOrder order);

    @Select("select * from longorder where stat = #{stat}")
    List<LongOrder> selectByStat(int stat);

//    @Select("select * from longorder where stat = #{stat} and #{date} between start_date and end_date")
//    List<LongOrder> selectByGidAndStatAndDate(@Param("gid") int gid, @Param("stat") int stat, @Param("date") Date date);

    @SelectProvider(type = SQLBuilder.class, method = "selectByGidAndStatAndDate")
    List<LongOrder> selectByGidAndStatAndDate(int gid, int stat, Date date);

    @Update("update longorder set stat = #{stat} where id = #{id}")
    int updateStatById(@Param(value = "id") int id, @Param(value = "stat") int stat);

    class SQLBuilder {
        public String insert(final LongOrder order) {
            return new SQL(){
                {
                    INSERT_INTO("longorder");
                    VALUES("gid", String.valueOf(order.getGid()));
                    VALUES("start_date", "'" + TimeUtil.formatDate(order.getStartDate()) + "'");
                    VALUES("end_date", "'" + TimeUtil.formatDate(order.getEndDate()) + "'");
                    if (order.getStartTime() != null) {
                        VALUES("start_time", "'" + TimeUtil.formatDateTime(order.getStartTime()) + "'");
                    }
                    if (order.getEndTime() != null) {
                        VALUES("end_time", "'" + TimeUtil.formatTime(order.getEndTime()) + "'");
                    }
                    VALUES("weekday", String.valueOf(order.getWeekday()));
                }
            }.toString();
        }

        public String selectByGidAndStatAndDate(int gid, int stat, Date date) {
            return new SQL() {{
                SELECT("*");
                FROM("longorder");
                WHERE("gid = " + gid);
                AND();
                WHERE("stat = " + stat);
                AND();
                WHERE("'" + TimeUtil.formatDate(date) + "' between start_date and end_date");
            }
            }.toString();
        }
    }

}