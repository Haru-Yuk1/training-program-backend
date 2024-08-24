package com.test.trainingprogrambackend.controller;

import com.test.trainingprogrambackend.entity.Student;
import com.test.trainingprogrambackend.mapper.StudentMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(maxAge = 36000)
@Api(tags = "学生信息操作")
public class StudentController {
    @Autowired
    private StudentMapper studentMapper;

    @ApiOperation("获取所有学生（测试用）")
    @GetMapping("/student")
    List<Student> getAllStudents() {
        List<Student> students=studentMapper.findAll();
        return students;
    }

//    @GetMapping("/student")
//    public List<Student> getStudent() {
//        List<Student> students=studentMapper.selectList(null);
//        System.out.println(students);
//        return students;
//    }

    @ApiOperation("通过手机注册")
    @PutMapping("/student/registerByPhone")
    public String registerByPhone(String phone,String password) {
        int success=studentMapper.registerByPhone(phone,password,1);

        return success==1?"success":"fail";
    }
    @ApiOperation("通过邮箱注册")
    @PutMapping("/student/registerByEmail")
    public String registerByEmail(String email,String password) {
        int success=studentMapper.registerByEmail(email,password,1);
        return success==1?"success":"fail";
    }

    @ApiOperation("通过手机登录")
    @PostMapping("/student/loginByPhone")
    public Student loginByPhone(String phone,String password) {
        Student student=studentMapper.loginByPhone(phone,password);
        return student;
    }
    @ApiOperation("通过邮箱登录")
    @PostMapping("/student/loginByEmail")
    public Student loginByEmail(String email,String password) {
        Student student=studentMapper.loginByEmail(email,password);
        return student;
    }

//    @ApiOperation("通过手机来更新信息")
//    @PutMapping("/update/Information")
//    public String updateStuInformation(String stu_name, String idcard, String college, String major, String classes, String stu_id, String nation, String address, String phone) {
//
//        int success=studentMapper.updateInformation(stu_name,idcard,college,major,classes,stu_id,nation,address,phone);
//        return success==1?"success":"fail";
//    }
//    @ApiOperation("通过邮箱来更新信息")
//    @PutMapping("/update/StuInformationByEmail")
//    public String updateStuInformationByEmail(String stu_name,String idcard,String college,String major,String classes, String stu_id,String nation,String address,String email) {
//        int success=studentMapper.updateInformationByEmail(stu_name,idcard,college,major,classes,stu_id,nation,address,email);
//        return success==1?"success":"fail";
//    }


}
