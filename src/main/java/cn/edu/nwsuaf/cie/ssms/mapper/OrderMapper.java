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

    @Select("select count(*) from `order` where `gid` = #{gid} and " +
            "(`start_time` >= #{endTime} or `end_time` <= #{startTime}) and `stat` != #{stat}")
    int selectOrderNumsBetweenTimeByGroundAndExcludeStat(@Param(value = "gid") Integer ground,
                                                         @Param(value = "startTime") Date startTime,
                                                         @Param(value = "endTime") Date endTime,
                                                         @Param(value = "stat") Integer stat);

    @UpdateProvider(type = SQLBuilder.class, method = "buildUpdate")
    int updateByPrimaryKeySelective(Order record);

    class SQLBuilder {
        String buildUpdate(final Order order) {
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