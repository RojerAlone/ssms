package cn.edu.nwsuaf.cie.ssms.mapper;

import cn.edu.nwsuaf.cie.ssms.model.CloseInfo;
import cn.edu.nwsuaf.cie.ssms.model.Order;
import cn.edu.nwsuaf.cie.ssms.util.TimeUtil;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;
import java.util.List;

@Mapper
public interface CloseInfoMapper {

    @InsertProvider(type = SQLBuilder.class, method = "insert")
    int insert(CloseInfo closeInfo);

    @Select("select * from closeinfo where stat = #{stat} limit #{page}, #{nums}")
    List<CloseInfo> selectByStatAndNums(@Param("stat") int stat, @Param("page") int page, @Param("nums") int nums);

    @Update("update closeinfo set stat = #{stat} where id = #{id}")
    int updateStatById(@Param(value = "id") int id, @Param(value = "stat") int stat);

//    @Select("select * from closeinfo where gid = #{gid} and stat = #{stat} and #{startDate} between start_date and end_date")
//    List<CloseInfo> selectByGidAndStatAndCloseDate(@Param("gid") int gid, @Param("stat") int stat,
//                                                   @Param("startDate") Date startDate);
    @SelectProvider(type = SQLBuilder.class, method = "selectByGidAndStatAndCloseDate")
    List<CloseInfo> selectByGidAndStatAndCloseDate(int gid, int stat, Date startDate);

    /**
     * 根据闭馆信息的状态和时间查询
     * @param stat 闭馆信息的状态
     * @param startTime 开始时间，应该是当天零点
     * @return
     */
    @Select("select * from closeinfo where stat = #{stat} and start_time >= #{startTime}")
    List<Order> selectByStatAndTime(@Param(value = "stat") int stat, @Param(value = "startTime") Date startTime);

    class SQLBuilder {

        public String insert(final CloseInfo closeInfo) {
            return new SQL(){{
                INSERT_INTO("closeinfo");
                VALUES("gid", String.valueOf(closeInfo.getGid()));
                VALUES("start_date", "'" + TimeUtil.formatDate(closeInfo.getStartDate()) + "'");
                VALUES("end_date", "'" + TimeUtil.formatDate(closeInfo.getEndDate()) + "'");
                VALUES("start_time", "'" + TimeUtil.formatTime(closeInfo.getStartTime()) + "'");
                if (closeInfo.getEndTime() != null) {
                    VALUES("end_time", "'" + TimeUtil.formatTime(closeInfo.getEndTime()) + "'");
                }
                if (closeInfo.getReason() != null) {
                    VALUES("reason", closeInfo.getReason());
                }
            }
            }.toString();
        }

        public String selectByGidAndStatAndCloseDate(int gid, int stat, Date startDate) {
            return new SQL() {{
                SELECT("*");
                FROM("closeinfo");
                WHERE("gid = " + gid);
                AND();
                WHERE("stat = " + stat);
                AND();
                WHERE("'" + TimeUtil.formatDate(startDate) + "' between start_date and end_date");
            }
            }.toString();
        }
    }

}