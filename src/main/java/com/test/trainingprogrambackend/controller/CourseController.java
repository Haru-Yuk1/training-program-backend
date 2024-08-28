package com.test.trainingprogrambackend.controller;

import com.test.trainingprogrambackend.Service.TakesService;
import com.test.trainingprogrambackend.entity.*;
import com.test.trainingprogrambackend.mapper.CourseMapper;
import com.test.trainingprogrambackend.mapper.StudentMapper;
import com.test.trainingprogrambackend.mapper.TakesMapper;
import com.test.trainingprogrambackend.util.JwtUtils;
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

    @Autowired
    private StudentMapper studentMapper;

    @ApiOperation("直接获取所有课程")
    @GetMapping("/getAll")
    public List<Course> getAll() {
        List<Course> courses=courseMapper.selectAll();
        return courses;
    }

    @ApiOperation("通过名字获取课程")
    @PostMapping("/getByName")
    public List<CourseClassDTO> getCourseByName(@RequestParam String name) {
        List<CourseClassDTO> courseClassDTOS=courseMapper.getCourseByName(name);
        return courseClassDTOS;
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
    public List<Course> getCourseByCondition(@RequestBody CourseClassDTO courseClassDTO) {
        List<Course> course=courseMapper.selectByConditions(courseClassDTO.getCredit(),courseClassDTO.getType(),courseClassDTO.getDeptName(),courseClassDTO.getIsFull(),courseClassDTO.getTeacherName(),courseClassDTO.getCode());


        return course;
    }

//    @ApiOperation("通过条件筛选课程(这个包括了courseclass表的信息)")
//    @GetMapping("/getByCondition2")
//    public List<CourseClassDTO> getCourseByCondition(@RequestBody CourseClassDTO courseClassDTO) {
//        List<CourseClassDTO> courseClassDTOS=courseMapper.selectByConditions(courseClassDTO.getCredit(),courseClassDTO.getType(),courseClassDTO.getDeptName(),courseClassDTO.getIsFull());
//
//
//        return courseClassDTOS;
//    }
    @ApiOperation("通过学生Id来获取课程")
    @GetMapping("/getBystudentId")
    public List<CourseClassDTO> getCourseByStudentId(@RequestHeader("Authorization") String token) {
        String StudentIdCard= JwtUtils.getClaimsByToken(token).getSubject();

        Student student=studentMapper.findByIdCard(StudentIdCard);
        List<CourseClassDTO> courseClassDTOS=courseMapper.courseClassByStudentId(student.getStudentid());
        return courseClassDTOS;
    }

    @ApiOperation("选课操作")
    @PostMapping("/takeClass")
    public Result updateTakes(@RequestHeader("Authorization") String token, @RequestParam String classNumber) {
        String StudentIdCard= JwtUtils.getClaimsByToken(token).getSubject();

        Student student=studentMapper.findByIdCard(StudentIdCard);
        return  takesService.takesOperation(student.getStudentid(),classNumber);
    }
    @ApiOperation("获取选课表")
    @GetMapping("/getTakes")
    public List<Takes> getAllTakes() {
        return  takesMapper.findAll();
    }
    @ApiOperation("退课操作")
    @PostMapping("/dropClass")
    public Result deleteTakes(@RequestHeader("Authorization") String token, @RequestParam String classNumber) {
        String StudentIdCard= JwtUtils.getClaimsByToken(token).getSubject();

        Student student=studentMapper.findByIdCard(StudentIdCard);
        return  takesService.deleteTakesOperation(student.getStudentid(),classNumber);
    }


}
