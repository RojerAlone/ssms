package cn.edu.nwsuaf.cie.ssms.mapper;

import cn.edu.nwsuaf.cie.ssms.model.CloseInfo;
import cn.edu.nwsuaf.cie.ssms.model.Order;
import cn.edu.nwsuaf.cie.ssms.util.TimeUtil;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Mapper
public interface CloseInfoMapper {

    @InsertProvider(type = SQLBuilder.class, method = "buildInsert")
    int insert(CloseInfo closeInfo);

    @Select("select * from closeinfo where stat = #{stat}")
    List<CloseInfo> selectByStat(int stat);

    @Update("update closeinfo set stat = #{stat} where id = #{id}")
    int updateStatById(@Param(value = "id") int id, @Param(value = "stat") int stat);

    @Select("select * from closeinfo where gid = #{gid} and stat = #{stat} and close_date = #{closeDate}")
    List<CloseInfo> selectByGidAndStatAndCloseDate(@Param("gid") int gid, @Param("stat") int stat,
                                                   @Param("closeDate") Date closeDate);

    /**
     * 根据闭馆信息的状态和时间查询
     * @param stat 闭馆信息的状态
     * @param startTime 开始时间，应该是当天零点
     * @return
     */
    @Select("select * from closeinfo where stat = #{stat} and start_time >= #{startTime}")
    List<Order> selectByStatAndTime(@Param(value = "stat") int stat, @Param(value = "startTime") Date startTime);

    class SQLBuilder {

        public String buildInsert(final CloseInfo closeInfo) {
            return new SQL(){{
                INSERT_INTO("closeinfo");
                VALUES("gid", String.valueOf(closeInfo.getGid()));
                VALUES("close_date", "'" + TimeUtil.DATE_FORMATTER.format(closeInfo.getCloseDate()) + "'");
                VALUES("start_time", "'" + TimeUtil.TIME_FORMATTER.format(closeInfo.getStartTime()) + "'");
                if (closeInfo.getEndTime() != null) {
                    VALUES("end_time", "'" + TimeUtil.TIME_FORMATTER.format(closeInfo.getEndTime()) + "'");
                }
                if (closeInfo.getReason() != null) {
                    VALUES("reason", closeInfo.getReason());
                }
            }
            }.toString();
        }
    }

}