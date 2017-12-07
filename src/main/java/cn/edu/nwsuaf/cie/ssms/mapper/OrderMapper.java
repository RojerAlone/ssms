package cn.edu.nwsuaf.cie.ssms.mapper;

import cn.edu.nwsuaf.cie.ssms.model.Order;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

@Mapper
public interface OrderMapper {

    @Insert("insert into `order`(uid, gid, start_time, end_time, total) values (#{order.uid}, #{order.gid}, " +
            "#{order.startTime}, #{order.endTime}, #{order.total})")
    @Options(useGeneratedKeys = true)
    int insert(@Param(value = "order") Order order);

    @Select("select * from `order` where id = #{id}")
    Order selectByPrimaryKey(Integer id);

//    @Select("select count(`id`) from `order` where `gid` = #{gid} and `stat` != #{stat} " +
//            "and (`start_time` <= #{startTime} and `end_time` >= #{startTime}) " +
//            "or (`start_time` <= #{endTime} and `end_time` >= #{endTime})")
    @Select("select count(`id`) from `order` where `gid` = #{gid} and `stat` != #{stat} " +
            "and `start_time` not between #{startTime} and #{endTime} " +
            "and `end_time` not between #{startTime} and #{endTime}")
    int selectNumsBetweenTimeByGroundAndExcludeStat(@Param(value = "gid") Integer ground,
                                                    @Param(value = "startTime") Date startTime,
                                                    @Param(value = "endTime") Date endTime,
                                                    @Param(value = "stat") Integer stat);

    @Update("update `order` set stat=#{stat} where id = #{id}")
    int updateStatById(@Param(value = "id") int id, @Param(value = "stat") int stat);

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