package com.test.trainingprogrambackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.trainingprogrambackend.entity.Message;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    //添加消息
    @Insert("insert into message (title,content,publishDate) values (#{title},#{content},#{publishDate})")
    int insert(Message message);

    //修改通知
    @UpdateProvider(type = SqlProvider.class, method = "updateMessage")
    int update(@Param("message") Message message);

    class SqlProvider {
        public String updateMessage(@Param("message") Message message) {
            return new SQL() {{
                UPDATE("message");
                if (message.getTitle() != null && !message.getTitle().isEmpty()) {
                    SET("title = #{message.title}");
                }
                if (message.getContent() != null && !message.getContent().isEmpty()) {
                    SET("content = #{message.content}");
                }
                if (message.getPublishDate() != null) {
                    SET("publishDate = #{message.publishDate}");
                }
                WHERE("title = #{message.originalTitle}");
            }}.toString();
        }
    }

    //删除通知
    @Delete("delete from message where title = #{title}")
    int delete(String title);

    //查询所有通知公告
    @Select("select * from message")
    List<Message> query();
}
