package com.test.trainingprogrambackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.trainingprogrambackend.entity.Dormitory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DormitoryMapper extends BaseMapper<Dormitory> {
    @Select("select dorName, classes, peoNumber from dormitory where classes = #{classes} and isFull = 0 limit 1")
    Dormitory getByClassesNotNullOne(String classes);

    @Select("select dorName, classes, peoNumber from dormitory where classes = #{classes} and isFull = 0")
    List<Dormitory> getByClassesNotNullAll(String classes);

    @Select("select peoNumber from dormitory where dorName = #{dorName}")
    Dormitory getpeoNumberByDorName(String dorName);

    @Update("update dormitory set isFull = 1 where dorName = #{dorName}")
    boolean updateFull(String dorName);

    @Update("update dormitory set isFull = 0 where dorName = #{dorName}")
    boolean updateNotFull(String dorName);

    @Update("update dormitory set peoNumber = peoNumber + 1 where dorName = #{dorName}")
    boolean updatePeoNumberAdd(String dorName);

    @Update("update dormitory set peoNumber = peoNumber - 1 where dorName = #{dorName}")
    boolean updatePeoNumberMinus(String dorName);

    @Select("select dorName from dormitory")
    @Results({
            @Result(column = "dorName", property = "dorName"),
            @Result(column = "dorName", property = "students", javaType = List.class,
                many = @Many(select = "com.test.trainingprogrambackend.mapper.StudentMapper.findAllByDorName"))
    })
    List<Dormitory> getDormitoryWithStudent();


}
