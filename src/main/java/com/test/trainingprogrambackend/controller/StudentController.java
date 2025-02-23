package com.test.trainingprogrambackend.controller;

import com.test.trainingprogrambackend.entity.Student;
import com.test.trainingprogrambackend.mapper.StudentMapper;
import com.test.trainingprogrambackend.util.JwtUtils;
import com.test.trainingprogrambackend.util.MD5Util;
import com.test.trainingprogrambackend.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation("检测是否有身份证（如果已被激活就返回已激活）")
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

    @ApiOperation("检测是否有身份证（如果有就返回学生信息）")
    @PostMapping("/checkIdCard2")
    public Result checkIdCard2(@RequestParam String IdCard) {
        Student student=studentMapper.findByIdCard(IdCard);
        if(student==null) {
            return Result.error().message("未找到该身份证").code(20001);
        }

        return Result.ok().data("student",student).message("检测到你的身份证").code(20000);
    }


    @ApiOperation("用身份证+邮箱激活账户(同时更新邮箱、密码、激活状态)")
    @PostMapping("/registerByEmail")
    public Result registerStudentByEmail(@RequestParam String IdCard,@RequestParam String email,@RequestParam String password) {
        Student student=studentMapper.findByIdCard(IdCard);
        if(student==null){
            return Result.error().message("未找到该身份证").code(20001);
        }
        if(student.getStatus()==1){
            return Result.error().message("该账号已被激活").code(20002);
        }
        if(studentMapper.selectByEmail(email)!=null){
            return Result.error().message("该邮箱已被注册").code(20003);
        }
        //加密密码
        String encryptedPassword= MD5Util.encrypt(password);

        int success=studentMapper.registerByEmail(IdCard,email,encryptedPassword,1);
        if(success==1){
            return Result.ok().message("账号激活成功").code(20000);
        }
        return  Result.error().message("账号激活失败").code(20001);
    }

    @ApiOperation("用身份证+电话激活账户")
    @PostMapping("/registerByPhone")
    public Result registerStudentByPhone(@RequestParam String IdCard,@RequestParam String phone,@RequestParam String password) {

        Student student=studentMapper.findByIdCard(IdCard);
        if(student==null){
            return Result.error().message("未找到该身份证").code(20001);
        }
        if(student.getStatus()==1){
            return Result.error().message("该账号已被激活").code(20002);
        }
        if(studentMapper.selectByPhone(phone)!=null){
            return Result.error().message("该电话已被注册").code(20003);
        }
        //加密密码
        String encryptedPassword= MD5Util.encrypt(password);

        int success=studentMapper.registerByPhone(IdCard,phone,encryptedPassword,1);
        if(success==1){
            return Result.ok().message("账号激活成功").code(20000);
        }
        return  Result.error().message("账号激活失败").code(20001);
    }

    @ApiOperation("通过手机+密码登录（会创建一个token，使用info时header要返回token）")
    @PostMapping("/loginByPhoneAndPassword")
    public Result loginByPhoneAndPassword(@RequestParam String phone,@RequestParam String password) {
        String encryptedPassword= MD5Util.encrypt(password);

        Student student=studentMapper.loginByPhoneAndPassword(phone,encryptedPassword);
        if(student==null){
            return Result.error().message("");
        }

        String token = JwtUtils.generateToken(student.getIdCard());

        return Result.ok().data("token",token);
    }
    @ApiOperation("通过邮箱+密码登录（会创建一个token，使用info时header要返回token）")
    @PostMapping("/loginByEmailAndPassword")
    public Result loginByEmailAndPassword(@RequestParam String email,@RequestParam String password) {
        String encryptedPassword= MD5Util.encrypt(password);

        Student student=studentMapper.loginByEmailAndPassword(email,encryptedPassword);
        if(student==null){
            return Result.error().message("密码错误");
        }

        String token = JwtUtils.generateToken(student.getIdCard());

        return Result.ok().data("token",token);
    }


    @ApiOperation("用邮箱+验证码登录的check")
    @PostMapping("/checkEmail")
    public Result checkEmail(@RequestParam String email) {
        if(studentMapper.selectByEmail(email)==null){
            return Result.error().message("未找到该邮箱");
        }
        return Result.ok().message("找到该邮箱");
    }

    @ApiOperation("用电话+验证码登录的check")
    @PostMapping("/checkPhone")
    public Result checkPhone(@RequestParam String phone) {
        if(studentMapper.selectByPhone(phone)==null){
            return Result.error().message("未找到该电话");
        }
        return Result.ok().message("找到该电话");
    }
    @ApiOperation("通过邮箱+验证码登录（会创建一个token，使用info时header要返回token）")
    @PostMapping("/loginByEmailAndCode")
    public Result loginByEmailAndCode(@RequestParam String email) {

        if(studentMapper.selectByEmail(email)==null){
            return Result.error().message("未找到该邮箱");
        }

        Student student=studentMapper.loginByEmailAndCode(email);

        String token = JwtUtils.generateToken(student.getIdCard());


        return Result.ok().data("token",token);
    }

    @ApiOperation("通过手机+验证码登录（会创建一个token，使用info时header要返回token）")
    @PostMapping("/loginByPhoneAndCode")
    public Result loginByPhoneAndCode(@RequestParam String phone) {
        if(studentMapper.selectByEmail(phone)==null){
            return Result.error().message("未找到该电话");
        }

        Student student=studentMapper.loginByPhoneAndCode(phone);

        String token = JwtUtils.generateToken(student.getIdCard());

        return Result.ok().data("token",token);
    }

    @ApiOperation("登录后使用token来获取信息(使用学生的身份证号来获取)")
    @GetMapping("/info")
    public Result info(@RequestHeader("Authorization") String token) {
        String StudentIdCard=JwtUtils.getClaimsByToken(token).getSubject();
        Student student=studentMapper.findByIdCard(StudentIdCard);
        return Result.ok().data("Student",student).message("成功获取学生");
    }

    @ApiOperation("忘记密码通过邮箱找回")
    @PostMapping("/forgetPasswordByEmail")
    public Result forgetPasswordByEmail(@RequestParam String email,@RequestParam String password) {
        Student student=studentMapper.findByEmail(email);
        if(student==null){
            return Result.error().message("未找到该邮箱");
        }
        String encryptedPassword= MD5Util.encrypt(password);
        int success=studentMapper.updatePasswordByEmail(email,encryptedPassword);
        if(success==1){
            return Result.ok().message("修改成功");
        }
        return Result.error().message("修改失败");
    }

    @ApiOperation("忘记密码通过手机找回")
    @PostMapping("/forgetPasswordByPhone")
    public Result forgetPasswordByPhone(@RequestParam String phone,@RequestParam String password) {
        Student student=studentMapper.findByPhone(phone);
        if(student==null){
            return Result.error().message("未找到该手机");
        }
        String encryptedPassword= MD5Util.encrypt(password);

        int success=studentMapper.updatePasswordByPhone(phone,encryptedPassword);

        if(success==1){
            return Result.ok().message("修改成功");
        }
        return Result.error().message("修改失败");
    }

    @PostMapping("/logout")
    public Result logout() {

        return Result.ok();
    }

//    @ApiOperation("登录后使用token来获取信息(使用学生的身份证号来获取)")
//    @GetMapping("/info2")
//    public Result info2(String idCard) {
//
//        Student student=studentMapper.findByIdCard(idCard);
//        return Result.ok().data("Student",student).message("成功获取学生");
//    }

    @ApiOperation("更新信息,要(email，address，idCard)")
    @PostMapping("/updateInfo")
    public Result updateInfo(@RequestBody Student student,@RequestHeader("Authorization") String token) {
        String StudentIdCard=JwtUtils.getClaimsByToken(token).getSubject();
        Student studentOld=studentMapper.findByIdCard(StudentIdCard);

        if(studentOld==null){
            return Result.error().message("未找到该学生");
        }

//        if(studentMapper.findByIdCard(student.getIdCard())==null){
//            return Result.error().message("未找到该学生");
//        }
//        int success=studentMapper.updateStudentInfo(student.getPhone(),student.getEmail(),student.getAddress(),student.getIdCard());


        int success=studentMapper.updateStudentInfo(student.getEmail(),student.getAddress(),StudentIdCard);
        if(success==1){
            return Result.ok().message("更新成功");
        }
        return Result.error().message("更新失败");
    }





}
