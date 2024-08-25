package com.test.trainingprogrambackend.controller;

import com.test.trainingprogrambackend.entity.Course;
import com.test.trainingprogrambackend.entity.CourseClassDTO;
import com.test.trainingprogrambackend.mapper.CourseMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "课程操作")
@CrossOrigin(maxAge = 50000)
public class CourseController {
    @Autowired
    private CourseMapper courseMapper;



    @ApiOperation("直接获取所有课程")
    @GetMapping("/course/getAll")
    public List<Course> getAll() {
        List<Course> courses=courseMapper.selectAll();
        return courses;
    }

    @ApiOperation("通过名字获取课程")
    @GetMapping("/course/getByName")
    public List<Course> getCourseByName(String name) {
        List<Course> courses=courseMapper.getCourseByName(name);
        return courses;
    }
    @ApiOperation("通过编号获取课程")
    @GetMapping("/course/getByCode")
    public List<Course> getCourseByCode(String code) {
        List<Course> courses=courseMapper.getCourseByCode(code);
        return courses;
    }

    @ApiOperation("通过名字关键词搜索获取课程")
    @GetMapping("/course/getByNameLike")
    public List<Course> getCourseByNameLike(String namelike) {
        System.out.println(namelike);
        List<Course> courses=courseMapper.getCourseByNameLike(namelike);
        return courses;
    }

    @ApiOperation("通过条件筛选课程(这个包括了courseclass表的信息)")
    @GetMapping("/course/getByCondition")
    public List<CourseClassDTO> getCourseByCondition(String code, String name, Integer credit, String type, String deptName, String classNumber, String teacherName, Integer capacity, Integer selectedNumber, Integer isFull) {
        List<CourseClassDTO> courseClassDTOS=courseMapper.selectByConditions(code,name,credit,type,deptName,classNumber,teacherName,capacity,selectedNumber,isFull);


        return courseClassDTOS;
    }
    @ApiOperation("通过学生Id来获取课程")
    @GetMapping("/course/getBystudentId")
    public List<CourseClassDTO> getCourseByStudentId(String studentId) {
        List<CourseClassDTO> courseClassDTOS=courseMapper.courseClassByStudentId(studentId);
        return courseClassDTOS;
    }


}
