package com.test.trainingprogrambackend.controller;

import com.test.trainingprogrambackend.entity.StudentStatistics;
import com.test.trainingprogrambackend.mapper.StudentMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api("后台管理系统操作")
public class BackstageController {
    @Autowired
    private StudentMapper studentMapper;

    //返回完成账号激活、选课总人数、选择申请宿舍、信息录入、注册以及总学生数
    @GetMapping("/backstage/registrationNum")
    public StudentStatistics registrationNum() {
        return studentMapper.findAllStatistics();
    }

//    //各班级完成注册人数
//    @GetMapping("/backstage/classRegistrationNum")
//    public List<Integer> classRegistrationNum() {
//
//    }
//
//    //各班级完成信息录入人数
//    @GetMapping("/backstage/classLogNum")
//    public List<Integer> classLogNum() {
//
//    }
//
//    //各班级完成选择申请宿舍人数
//    @GetMapping("/backstage/classApplyDorNum")
//    public List<Integer> classApplyDorNum() {
//
//    }
//
//    //各班级完成选课人数
//    @GetMapping("/backstage/classChooseCourseNum")
//    public List<Integer> classChooseCourseNum() {
//
//    }

}
