package com.test.trainingprogrambackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.trainingprogrambackend.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseMapper {
    @Select("select * from course")
    List<Course> selectAll();

    @Select("select * from course where name=#{name}")
    List<Course> getCourseByName(String name);
    @Select("select * from course where code=#{code}")
    List<Course> getCourseByCode(String code);

    @Select("select * from course where name like concat('%',#{name},'%')")
    List<Course> getCourseByNameLike(@Param("name") String name);

    @Select("<script>"+"select * from course NATURAL JOIN courseclass where 1=1"
            +"<if test='code!=null'> AND code=#{code}</if>"
            +"<if test='name!=null'> AND name=#{name}</if>"
            +"<if test='credit!=null'> AND credit=#{credit}</if>"
            +"<if test='type!=null'> AND type=#{type}</if>"
            +"<if test='deptName!=null'> AND deptName=#{deptName}</if>"
            +"<if test='classNumber!=null'> AND classNumber=#{classNumber}</if>"
            +"<if test='teacherName!=null'> AND teacherName=#{teacherName}</if>"
            +"<if test='capacity!=null'> AND capacity=#{capacity}</if>"
            +"<if test='selectedNumber!=null'> AND selectedNumber=#{selectedNumber}</if>"
            +"<if test='isFull!=null'> AND isFull=#{isFull}</if>"
            +"</script>")
    List<Course> selectByConditions(String code,String name,Integer credit,String type,String deptName,String classNumber,String teacherName,Integer capacity,Integer selectedNumber,Integer isFull);
}
