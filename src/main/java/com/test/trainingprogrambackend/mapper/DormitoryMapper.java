package com.test.trainingprogrambackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.trainingprogrambackend.entity.Dormitory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DormitoryMapper extends BaseMapper<Dormitory> {
    @Select("select dorName, classes, peoNumber from dormitory where classes = #{classes} and isFull = 0 limit 1")
    Dormitory getByClassesNotNull(String classes);

    @Update("update dormitory set isFull = 1 where dorName = #{dorName}")
    void updateIsFull(String dorName);

    @Update("update dormitory set peoNumber = peoNumber + 1 where dorName = #{dorName}")
    void updatePeoNumber(String dorName);

    @Select("select dorName from dormitory")
    @Results({
            @Result(column = "dorName", property = "dorName"),
            @Result(column = "dorName", property = "students", javaType = List.class,
                many = @Many(select = "com.test.trainingprogrambackend.mapper.StudentMapper.findAllByDorName"))
    })
    List<Dormitory> getDormitoryWithStudent();
}
