package com.test.trainingprogrambackend.mapper;

import com.test.trainingprogrambackend.entity.Section;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SectionMapper {
    @Select("SELECT classroom, week, day, timeId FROM section where classNumber = #{classNumber}")
    List<Section> getAllSections(String classNumber);
}
