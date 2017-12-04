package cn.edu.nwsuaf.cie.ssms.mapper;

import cn.edu.nwsuaf.cie.ssms.model.LongOrder;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

@Mapper
public interface LongOrderMapper {

    @InsertProvider(type = SQLBuilder.class, method = "buildInsert")
    int insert(LongOrder order);

    @Select("select * from longorder where stat = #{stat}")
    List<LongOrder> selectByStat(int stat);

    class SQLBuilder {
        public String buildInsert(LongOrder order) {
            return new SQL(){
                {
                    INSERT_INTO("longorder");
                    VALUES("gid", String.valueOf(order.getGid()));
                    VALUES("startTime", order.getStartTime().toString());
                    if (order.getEndTime() != null) {
                        VALUES("endTime", order.getEndTime().toString());
                    }
                }
            }.toString();
        }
    }

}