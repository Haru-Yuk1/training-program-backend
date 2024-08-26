package com.test.trainingprogrambackend.controller;

import com.test.trainingprogrambackend.entity.Student;
import com.test.trainingprogrambackend.mapper.StudentMapper;
import com.test.trainingprogrambackend.util.JwtUtils;
import com.test.trainingprogrambackend.util.Result;
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
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentMapper studentMapper;

    @ApiOperation("获取所有学生（测试用）")
    @GetMapping("/test")
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
    @ApiOperation("检测是否有身份证")
    @PostMapping("/checkIdCard")
    public Result checkIdCard(@RequestParam String IdCard) {
        Student student=studentMapper.findByIdCard(IdCard);
        if(student==null) {
            return Result.error().message("未找到该身份证").code(20001);
        }
        if(student.getStatus()==1){
            return Result.error().message("该账号已被激活").code(20002);
        }
        return Result.ok().message("检测到你的身份证").code(20000);
    }

    @ApiOperation("用身份证激活账户(同时更新邮箱、密码、激活状态)")
    @PostMapping("/register")
    public Result registerStudent(@RequestParam String IdCard,@RequestParam String email,@RequestParam String password) {
        Student student=studentMapper.findByIdCard(IdCard);
        if(student==null){
            return Result.error().message("未找到该身份证").code(20001);
        }
        if(student.getStatus()==1){
            return Result.error().message("该账号已被激活").code(20002);
        }

        int success=studentMapper.register(IdCard,email,password,1);
        if(success==1){
            return Result.ok().message("账号激活成功").code(20000);
        }
        return  Result.error().message("账号激活失败").code(20001);
    }




//    @ApiOperation("通过手机注册")
//    @PutMapping("/registerByPhone")
//    public Result registerByPhone(String phone, String password) {
//        int success=studentMapper.registerByPhone(phone,password,1);
//        if(success==1){
//            return Result.ok().message("注册成功");
//        }
//        return Result.error().message("注册失败");
//    }
//    @ApiOperation("通过邮箱注册")
//    @PutMapping("/registerByEmail")
//    public Result registerByEmail(String email,String password) {
//        int success=studentMapper.registerByEmail(email,password,1);
//        if(success==1){
//            return Result.ok().message("注册成功");
//        }
//        return Result.error().message("注册失败");
//    }

    @ApiOperation("通过手机登录（会创建一个token，使用info时header要返回token）")
    @PostMapping("/loginByPhone")
    public Result loginByPhone(String phone,String password) {
        Student student=studentMapper.loginByPhone(phone,password);
        String token = JwtUtils.generateToken(student.getName());

        return Result.ok().data("token",token);
    }
    @ApiOperation("通过邮箱登录（会创建一个token，使用info时header要返回token）")
    @PostMapping("/loginByEmail")
    public Result loginByEmail(String email,String password) {
        Student student=studentMapper.loginByEmail(email,password);
        String token = JwtUtils.generateToken(student.getIdCard());

        return Result.ok().data("token",token);
    }

    @ApiOperation("登录后使用token来获取信息(使用学生的身份证号来获取)")
    @GetMapping("/info")
    public Result info(@RequestHeader("Authorization") String token) {
        String StudentIdCard=JwtUtils.getClaimsByToken(token).getSubject();
        Student student=studentMapper.findByName(StudentIdCard);
        return Result.ok().data("Student",student).message("成功获取学生");
    }

    @PostMapping("/logout")
    public Result logout() {

        return Result.ok();
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
