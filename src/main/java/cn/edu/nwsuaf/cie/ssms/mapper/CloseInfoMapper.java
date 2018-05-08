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

    @Select("select count(id) from closeinfo where stat = #{stat}")
    int selectCountByStat(int stat);

    @Update("update closeinfo set stat = #{stat} where id = #{id}")
    int updateStatById(@Param(value = "id") int id, @Param(value = "stat") int stat);

    @Select("select * from closeinfo where stat = #{stat} and #{startDate} between start_date and end_date")
    List<CloseInfo> selectByGidAndStatAndCloseDate(@Param("stat") int stat, @Param("startDate") Date startDate);
//    List<CloseInfo> selectByGidAndStatAndCloseDate(int gid, int stat, Date startDate);

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
                VALUES("start_date", "#{startDate}");
                VALUES("end_date", "#{endDate}");
                if (closeInfo.getStartTime() != null) {
                    VALUES("start_time", "#{startTime}");
                }
                if (closeInfo.getEndTime() != null) {
                    VALUES("end_time", "#{endTime}");
                }
                if (closeInfo.getReason() != null) {
                    VALUES("reason", "#{reason}");
                }
            }
            }.toString();
        }
    }

}