package com.test.trainingprogrambackend.controller;

import com.test.trainingprogrambackend.Service.TakesService;
import com.test.trainingprogrambackend.entity.Course;
import com.test.trainingprogrambackend.entity.CourseClassDTO;
import com.test.trainingprogrambackend.entity.Takes;
import com.test.trainingprogrambackend.mapper.CourseMapper;
import com.test.trainingprogrambackend.mapper.TakesMapper;
import com.test.trainingprogrambackend.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "课程操作")
@CrossOrigin(maxAge = 50000)
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private TakesService takesService;
    @Autowired
    private TakesMapper takesMapper;


    @ApiOperation("直接获取所有课程")
    @GetMapping("/getAll")
    public List<Course> getAll() {
        List<Course> courses=courseMapper.selectAll();
        return courses;
    }

    @ApiOperation("通过名字获取课程")
    @GetMapping("/getByName")
    public List<Course> getCourseByName(String name) {
        List<Course> courses=courseMapper.getCourseByName(name);
        return courses;
    }
    @ApiOperation("通过编号获取课程")
    @GetMapping("/getByCode")
    public List<Course> getCourseByCode(String code) {
        List<Course> courses=courseMapper.getCourseByCode(code);
        return courses;
    }

    @ApiOperation("通过名字关键词搜索获取课程")
    @GetMapping("/getByNameLike")
    public List<Course> getCourseByNameLike(String namelike) {

        List<Course> courses=courseMapper.getCourseByNameLike(namelike);
        System.out.println(courses.get(0).getCode());
        return courses;
    }

    @ApiOperation("通过条件筛选课程(这个包括了courseclass表的信息)")
    @PostMapping("/getByCondition")
    public List<CourseClassDTO> getCourseByCondition(String code, String name, Float credit, String type, String deptName, String classNumber, String teacherName, Integer capacity, Integer selectedNumber, Integer isFull) {
        List<CourseClassDTO> courseClassDTOS=courseMapper.selectByConditions(code,name,credit,type,deptName,classNumber,teacherName,capacity,selectedNumber,isFull);


        return courseClassDTOS;
    }
    @ApiOperation("通过学生Id来获取课程")
    @GetMapping("/getBystudentId")
    public List<CourseClassDTO> getCourseByStudentId(String studentId) {
        List<CourseClassDTO> courseClassDTOS=courseMapper.courseClassByStudentId(studentId);
        return courseClassDTOS;
    }

    @ApiOperation("选课操作")
    @PostMapping("/updateTakes")
    public Result updateTakes(String studentId, String classNumber) {
        return  takesService.takesOperation(studentId,classNumber);
    }
    @ApiOperation("获取选课表")
    @GetMapping("/getTakes")
    public List<Takes> getAllTakes() {
        return  takesMapper.findAll();
    }

}
