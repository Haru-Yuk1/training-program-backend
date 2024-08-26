package com.test.trainingprogrambackend.controller;

import com.test.trainingprogrambackend.Service.DormitoryService;
import com.test.trainingprogrambackend.entity.Dormitory;
import com.test.trainingprogrambackend.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "分配宿舍")
public class DormitoryController {
    @Autowired
    private DormitoryService dormitoryService;

    // 后台管理系统接口
    @GetMapping("/dor/assignDor")
    @ApiOperation("后台分配宿舍并返回分配结果")
    public List<Dormitory> assignDor(){
        return dormitoryService.assignDor();
    }

    @GetMapping("/dor/getNotFullDorByClasses")
    @ApiOperation("后台获取对应班级未满宿舍信息")
    public List<Dormitory> getNotFullDorByClasses(String classes){
        return dormitoryService.findNotFullDorByClasses(classes);
    }

    @GetMapping("/dor/refreshDor")
    @ApiOperation("后台刷新宿舍信息")
    public List<Dormitory> refreshDor(){
        return dormitoryService.refreshDor();
    }

    @PostMapping("/dor/adjustByNotFullDor")
    @ApiOperation("后台调整学生至非满宿舍")
    public Result adjustByNotNullDor(String studentid, String dorName){
        dormitoryService.adjustDorByNotFullDor(studentid, dorName);
        return Result.ok().message("调整成功");
    }

    @PostMapping("/dor/adjustByExchange")
    @ApiOperation("后台交换学生宿舍")
    public Result adjustByExchange(String studentid1, String studentid2){
        dormitoryService.adjustDorByExchange(studentid1, studentid2);
        return Result.ok().message("交换成功");
    }
}
