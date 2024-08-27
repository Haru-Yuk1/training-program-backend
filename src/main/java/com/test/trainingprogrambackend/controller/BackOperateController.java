package com.test.trainingprogrambackend.controller;

import com.test.trainingprogrambackend.entity.Message;
import com.test.trainingprogrambackend.entity.Student;
import com.test.trainingprogrambackend.entity.User;
import com.test.trainingprogrambackend.mapper.MessageMapper;
import com.test.trainingprogrambackend.mapper.StudentMapper;
import com.test.trainingprogrambackend.mapper.UserMapper;
import com.test.trainingprogrambackend.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@Api(tags = "后台管理系统消息管理、学生管理、用户管理操作")
@RequestMapping("/backOperate")
public class BackOperateController {
    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private UserMapper userMapper;

    //消息操作
    @ApiOperation("注意不需要传送 date 和 id，传递 title 和 content")
    @PostMapping("/message/insert")
    public Result insertMessage(@RequestBody Message message){
        //设置发布日期为当前系统日期
        Date date = new Date();
        message.setPublishDate(date);
        return messageMapper.insert(message) == 1 ? Result.ok().message("插入成功") : Result.error().message("插入失败，插入消息数量不等于1");
    }

    @ApiOperation("传递 title 即可删除")
    @PostMapping("/message/delete")
    public Result deleteMessage(@RequestParam String title){
        return messageMapper.delete(title) == 1 ? Result.ok().message("删除成功") : Result.error().message("删除失败，删除消息数量不等于1");
    }

    @ApiOperation("不需要 id，传递新标题的 title 和 content 以及 原标题的 originalTitle")
    @PostMapping("/message/update")
    public Result updateMessage(@RequestBody Message message){
        //设置发布日期为当前系统日期
        Date date = new Date();
        message.setPublishDate(date);
        return messageMapper.update(message) == 1 ? Result.ok().message("更新成功") : Result.error().message("更新失败，更新消息数量不等于1");
    }

    @ApiOperation("查询所有消息")
    @GetMapping("/message/query")
    public List<Message> queryMessage(){
        return messageMapper.query();
    }

    //学生操作
    @ApiOperation("查询所有学生所有信息")
    @GetMapping("/student/query")
    public List<Student> queryStudent(){
        return studentMapper.findAll();
    }

    @ApiOperation("传递 studentid 进行删除")
    @PostMapping("/student/delete")
    public Result deleteStudent(@RequestParam String studentid){
        return studentMapper.deleteStudent(studentid) == 1 ? Result.ok().message("删除成功") : Result.error().message("删除失败，删除学生数量不等于1");
    }

    @ApiOperation("不需要 id，可以更新学生的信息包括插入所需要的信息和电话、邮箱、密码，另外需要传入原学生的 originalStudentid")
    @PostMapping("/student/update")
    public Result updateStudent(@RequestBody Student student){
        return studentMapper.updateStudent(student) == 1 ? Result.ok().message("更新成功") : Result.error().message("更新失败，更新学生数量不等于1");
    }

    @ApiOperation("注意不需要传送 id，传递新学生的所有信息（包括姓名、民族、性别、生日、身份证号码、地址、专业、班级、学号、所属学院）")
    @PostMapping("/student/insert")
    public Result insertStudent(@RequestBody Student student){
        return studentMapper.insertStudent(student) == 1 ? Result.ok().message("插入成功") : Result.error().message("插入失败，插入学生数量不等于1");
    }

    //用户操作
    @ApiOperation("查询所有用户")
    @GetMapping("/user/query")
    public List<User> queryUser(){
        return userMapper.query();
    }

    @ApiOperation("传递 username 进行删除")
    @PostMapping("/user/delete")
    public Result deleteUser(@RequestParam String username){
        return userMapper.delete(username) == 1 ? Result.ok().message("删除成功") : Result.error().message("删除失败，删除用户数量不等于1");
    }

    @ApiOperation("不需要 id，传递新用户的所有信息以及原用户的 originalUserName")
    @PostMapping("/user/update")
    public Result updateUser(@RequestBody User user){
        Date date = new Date();
        user.setCreateDate(date);
        return userMapper.update(user) == 1 ? Result.ok().message("更新成功") : Result.error().message("更新失败，更新用户数量不等于1");
    }

    @ApiOperation("注意不需要传送 id 和 date")
    @PostMapping("/user/insert")
    public Result insertUser(@RequestBody User user){
        Date date = new Date();
        user.setCreateDate(date);
        return userMapper.insert(user) == 1 ? Result.ok().message("插入成功") : Result.error().message("插入失败，插入用户数量不等于1");
    }

}
