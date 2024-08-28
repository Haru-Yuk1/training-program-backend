package com.test.trainingprogrambackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.trainingprogrambackend.entity.Course;
import com.test.trainingprogrambackend.entity.CourseClass;
import com.test.trainingprogrambackend.entity.CourseClassDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseMapper {
    //选择所有课程
    @Select("select * from course")
    List<Course> selectAll();
    //通过课程名字选择课程
    @Select("select * from course where name=#{name}")
    List<Course> getCourseByName(String name);
    //通过课程编码选择课程
    @Select("select * from course where code=#{code}")
    List<Course> getCourseByCode(String code);
    //通过课程名称关键字选择课程
    @Select("select * from course where name like concat('%',#{name},'%')")
    List<Course> getCourseByNameLike(@Param("name") String name);

    @Select("<script>"+"select distinct course.* from course NATURAL JOIN courseclass where 1=1"
            +"<if test='credit!=null'> AND credit=#{credit}</if>"
            +"<if test='type!=null and type!=\"\"'> AND type=#{type}</if>"
            +"<if test='deptName!=null and deptName!=\"\"'> AND deptName=#{deptName}</if>"
            +"<if test='isFull!=null'> AND isFull=#{isFull}</if>"
            +"<if test='teacherName!=null and teacherName!=\"\"'> AND teacherName=#{teacherName}</if>"
            +"<if test='code!=null and code!=\"\"'> AND code=#{code}</if>"
            +"</script>")
    List<Course> selectByConditions(Float credit, String type, String deptName, Integer isFull, String teacherName, String code);

    @Select("select * from course natural join courseclass natural join takes where studentid=#{studentid}")
    List<CourseClassDTO> courseClassByStudentId(@Param("studentid") String studentid);


}
