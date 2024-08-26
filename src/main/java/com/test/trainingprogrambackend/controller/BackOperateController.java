package com.test.trainingprogrambackend.controller;

import com.test.trainingprogrambackend.entity.Message;
import com.test.trainingprogrambackend.entity.Student;
import com.test.trainingprogrambackend.mapper.MessageMapper;
import com.test.trainingprogrambackend.mapper.StudentMapper;
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

    @GetMapping("/message/query")
    public List<Message> queryMessage(){
        return messageMapper.query();
    }

    //学生操作
    @GetMapping("/student/query")
    public List<Student> queryStudent(){
        return studentMapper.findAll();
    }

    //用户操作

}
