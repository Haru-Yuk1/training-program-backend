package com.test.trainingprogrambackend.mapper;

import com.test.trainingprogrambackend.entity.Feedback;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FeedbackMapper {
    @Insert("INSERT INTO feedback (content) VALUES (#{content})")
    int addFeedback(String content);

    @Select("SELECT * FROM feedback")
    List<Feedback> getAllFeedbacks();
}
