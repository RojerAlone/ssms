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