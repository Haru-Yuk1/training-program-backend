package com.test.trainingprogrambackend.mapper;

import com.test.trainingprogrambackend.entity.CourseClass;
import com.test.trainingprogrambackend.entity.Takes;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;


@Mapper
public interface TakesMapper {

    //查找所有选课表
    @Select("select * from takes")
    List<Takes> findAll();

    //选课操作

    //通过课程编号查找课程班级
    @Select("select * from courseclass where classNumber=#{classNumber}")
    CourseClass findByClassNumber(String classNumber);
    //查找课程是否为满
    @Select("select selectedNumber,capacity,isFull from courseclass where classNumber=#{classNumber}")
    public Map<String,Object> isClassFull(String classNumber);
    //插入新的选课
    @Insert("insert into takes (studentid,classNumber) values (#{studentid},#{classNumber})")
    public void insertTakes(String studentid,String classNumber);
    //更新课程表所选人数
    @Update("update courseclass set selectedNumber=#{selectedNumber} where classNumber=#{classNumber}")
    public void updateCourseClass(String classNumber,int selectedNumber);
    //更新是否为满
    @Update("update courseclass set isFull=#{isFull} where classNumber=#{classNumber}")
    public void updateFull(String classNumber,int isFull);

    //退课操作

    //根据学生id查找选课表，判断是否选课
    @Select("select * from takes where studentid=#{studentid}")
    public List<Takes> findByStudentid(String studentid);

    //删除所选课程
    @Delete("delete from takes where studentid=#{studentid}")
    public void deleteByStudentid(String studentid);



}
