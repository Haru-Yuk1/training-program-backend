package com.test.trainingprogrambackend.mapper;

import com.test.trainingprogrambackend.entity.Takes;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;


@Mapper
public interface TakesMapper {

    @Select("select * from takes")
    List<Takes> findAll();

    @Select("select selectedNumber,capacity,isFull from courseclass where classNumber=#{classNumber}")
    public Map<String,Object> isClassFull(String classNumber);

    @Insert("insert into takes (studentid,classNumber) values (#{studentid},#{classNumber})")
    public void insertTakes(String studentid,String classNumber);

    @Update("update courseclass set selectedNumber=#{selectedNumber} where classNumber=#{classNumber}")
    public void updateCourseClass(String classNumber,int selectedNumber);

    @Update("update courseclass set isFull=#{isFull} where classNumber=#{classNumber}")
    public void updateFull(String classNumber,int isFull);
}
