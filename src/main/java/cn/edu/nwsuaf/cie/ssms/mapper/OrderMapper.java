package cn.edu.nwsuaf.cie.ssms.mapper;

import cn.edu.nwsuaf.cie.ssms.model.Order;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderMapper {

    @Insert("insert into `order`(uid, gid, start_time, end_time, total) values (#{order.uid}, #{order.gid}, " +
            "#{order.startTime}, #{order.endTime}, #{order.total})")
    @Options(useGeneratedKeys = true, keyProperty = "order.id")
    int insert(@Param("order") Order order);

    @Select("select * from `order` where id = #{id}")
    Order selectByPrimaryKey(Integer id);

//    @Select("select count(`id`) from `order` where `gid` = #{gid} and `stat` != #{stat} " +
//            "and (`start_time` <= #{startTime} and `end_time` >= #{startTime}) " +
//            "or (`start_time` <= #{endTime} and `end_time` >= #{endTime})")
    @Select("select count(`id`) from `order` where `gid` = #{gid} and `stat` != #{stat} " +
            "and (`start_time` between #{startTime} and #{endTime} " +
            "or `end_time` between #{startTime} and #{endTime})")
    int selectNumsBetweenTimeByGroundAndExcludeStat(@Param("gid") Integer ground,
                                                    @Param("startTime") Date startTime,
                                                    @Param("endTime") Date endTime,
                                                    @Param("stat") Integer stat);

    @Select("select * from `order` where uid = #{uid} limit #{nums}")
    List<Order> selectByUid(@Param("uid") String uid, @Param("nums") int nums);

    @Select("select * from `order` where uid = #{uid} and stat = #{stat} limit #{nums}")
    List<Order> selectByUidAndStat(@Param("uid") String uid, @Param("stat") int stat,
                                   @Param("page") int page, @Param("nums") int nums);

    @Select("select * from `order` where uid = #{uid} and stat = #{stat} and (`start_time` between #{startDate} and #{endDate})")
    List<Order> selectByStatAndUidAndTime(@Param("uid") String uid, @Param("stat") int stat,
                                          @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 使用 TO_DAYS() 函数来获取从公元0年到指定日期经过的天数，从而确定是不是同一天
     * 同时这里的 SQL 语句混入了业务逻辑，直接获取了羽毛球馆或者健美操室的信息
     */
    @Select("select * from `order` where gid <= 12 and stat != #{stat} and TO_DAYS(start_time)=TO_DAYS(#{date})")
    List<Order> selectBadmintonByDateAndExcludeStat(@Param("stat") int stat, @Param("date") Date date);

    @Select("select * from `order` where gid = 13 and stat = #{stat} and TO_DAYS(start_time) >= TO_DAYS(#{startDate}) and TO_DAYS(start_time) <= TO_DAYS(#{endDate})")
    List<Order> selectGymnasticsByDatesAndStat(@Param("stat") int stat, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Select("select * from `order` where stat = #{stat} and TO_DAYS(start_time) >= TO_DAYS(#{startDate}) and TO_DAYS(start_time) <= TO_DAYS(#{endDate})")
    List<Order> selectByStatAndDates(@Param("stat") int stat, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Update("update `order` set stat=#{stat} where id = #{id}")
    int updateStatById(@Param("id") int id, @Param("stat") int stat);

    @UpdateProvider(type = SQLBuilder.class, method = "buildUpdate")
    int updateByPrimaryKeySelective(Order order);


    class SQLBuilder {
        public String buildUpdate(final Order order) {
            return new SQL() {
                {
                    UPDATE("order");
                    if (order.getPayType() != null) {
                        SET("pay_type = #{order.payType}");
                    }
                    if (order.getStat() != null) {
                        SET("stat = #{order.stat}");
                    }
                    WHERE("id = #{order.id}");
                }
            }.toString();
        }
    }
}