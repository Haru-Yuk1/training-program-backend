package com.test.trainingprogrambackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.trainingprogrambackend.entity.Dormitory;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface DormitoryMapper extends BaseMapper<Dormitory> {
    @Select("select dorName, classes, peoNumber from dormitory where classes = #{classes} and isFull = 0 limit 1")
    Dormitory getByClassesNotNullOne(String classes);

    @Select("select dorName, classes, peoNumber from dormitory where classes = #{classes} and isFull = 0")
    List<Dormitory> getByClassesNotNullAll(String classes);

    @Select("select dorName, classes, peoNumber from dormitory where dorName = #{dorName}")
    Dormitory getByDorName(String dorName);

    @Select("select peoNumber from dormitory where dorName = #{dorName}")
    Dormitory getpeoNumberByDorName(String dorName);

    @Update("update dormitory set isFull = 1 where dorName = #{dorName}")
    int updateFull(String dorName);

    @Update("update dormitory set isFull = 0 where dorName = #{dorName}")
    int updateNotFull(String dorName);

    @Update("update dormitory set peoNumber = peoNumber + 1 where dorName = #{dorName}")
    int updatePeoNumberAdd(String dorName);

    @Update("update dormitory set peoNumber = peoNumber - 1 where dorName = #{dorName}")
    int updatePeoNumberMinus(String dorName);

    @Select("select dorName from dormitory")
    @Results({
            @Result(column = "dorName", property = "dorName"),
            @Result(column = "dorName", property = "students", javaType = List.class,
                many = @Many(select = "com.test.trainingprogrambackend.mapper.StudentMapper.findAllByDorName"))
    })
    List<Dormitory> getDormitoryWithStudent();

    // 统计各园区的床位总数（一个宿舍固定4个床位）
    @Select("SELECT areaName, COUNT(*) * 4 AS totalBeds " +
            "FROM dormitory " +
            "GROUP BY areaName")
    List<Map<String, Object>> countTotalBedsByArea();

    // 统计各园区的入住人数
    @Select("SELECT areaName, SUM(peoNumber) AS totalResidents " +
            "FROM dormitory " +
            "GROUP BY areaName")
    List<Map<String, Object>> countTotalResidentsByArea();

}
