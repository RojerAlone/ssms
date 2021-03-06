package cn.edu.nwsuaf.cie.ssms.mapper;

import cn.edu.nwsuaf.cie.ssms.model.Message;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * Created by zhangrenjie on 2018-04-23
 */
@Mapper
public interface MessageMapper {

    @InsertProvider(type = SQLBuilder.class, method = "buildInsert")
    @Options(useGeneratedKeys = true, keyProperty = "message.id")
    int insert(Message message);

    @Select("select * from `message` where id = #{id}")
    Message selectById(int id);

    @Select("select * from `message` where stat = #{stat} order by id desc limit #{startPos}, #{size}")
    List<Message> selectByStatAndPage(@Param("stat") int stat, @Param("startPos") int startPos, @Param("size") int size);

    @Select("select count(id) from `message` where stat = #{stat}")
    int selectCountByStat(int stat);

    @Update("update `message` set stat = #{stat} where id = #{id}")
    int updateStatById(@Param("stat") int stat, @Param("id") int id);

    class SQLBuilder {
        public String buildInsert(final Message message) {
            return new SQL() {
                {
                    INSERT_INTO("message");
                    if (message.getUid() != null) {
                        VALUES("uid", "#{uid}");
                    }
                    if (message.getTitle() != null) {
                        VALUES("title", "#{title}");
                    }
                    VALUES("content", "#{content}");
                }
            }.toString();
        }
    }

}
