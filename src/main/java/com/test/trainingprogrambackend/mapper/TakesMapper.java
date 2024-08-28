package com.test.trainingprogrambackend.mapper;

import com.test.trainingprogrambackend.entity.Course;
import com.test.trainingprogrambackend.entity.CourseClass;
import com.test.trainingprogrambackend.entity.Department;
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
    Map<String,Object> isClassFull(String classNumber);
    //插入新的选课
    @Insert("insert into takes (studentid,classNumber) values (#{studentid},#{classNumber})")
    void insertTakes(String studentid, String classNumber);
    //更新课程表所选人数
    @Update("update courseclass set selectedNumber=#{selectedNumber} where classNumber=#{classNumber}")
    void updateCourseClass(String classNumber, int selectedNumber);
    //更新是否为满
    @Update("update courseclass set isFull=#{isFull} where classNumber=#{classNumber}")
    void updateFull(String classNumber, int isFull);

    //退课操作

    //根据学生id查找选课表，判断是否选课
    @Select("select * from takes where studentid=#{studentid}")
    public List<Takes> findByStudentid(String studentid);

    //删除所选课程
    @Delete("delete from takes where studentid=#{studentid} and classNumber=#{classNumber}")
    public void deleteByStudentid(String studentid,String classNumber);

    //课程操作

    //插入前查找是否有符合学院
    @Select("select * from department where deptName=#{deptName}")
    Department departByDeptName(String deptName);

    @Insert("insert into course VALUES (#{code},#{name},#{credit},#{type},#{deptName})")
    int insertCourse(String code,String name,Float credit,String type,String deptName);

    //删除课程

    //删除前判断有无课程
    @Select("select * from course where code=#{code}")
    Course courseByCode(String code);
    @Delete("delete from course where code=#{code}")
    int deleteCourse(String code);

    //更新课程 除code以外
    @Update("update course set name=#{name},credit=#{credit},type=#{type},deptName=#{deptName} where code=#{code}")
    int updateCourse(String code,String name,Float credit,String type,String deptName);


    //课程班级操作

    //操作前判断有无课程班级
    @Select("select * from courseclass where classNumber=#{classNumber}")
    CourseClass findClassNumber(String classNumber);

    //插入前判断有无课程

    @Insert("insert into courseclass values (#{classNumber},#{teacherName},#{capacity},#{selectedNumber},#{isFull},#{code})")
    int insertCourseClass(String classNumber,String teacherName,Integer capacity,Integer selectedNumber,Integer isFull,String code);

    //删除课程班级
    @Delete("delete from courseclass where classNumber=#{classNumber}")
    int deleteCourseClass(String classNumber);

    //更新课程班级 除classNumber 和code
    @Update("update courseclass set teacherName=#{teacherName},capacity=#{capacity},selectedNumber=#{selectedNumber},isFull=#{isFull} where classNumber=#{classNumber}")
    int updateByclassNumber(String teacherName,Integer capacity,Integer selectedNumber,Integer isFull,String classNumber);

}
