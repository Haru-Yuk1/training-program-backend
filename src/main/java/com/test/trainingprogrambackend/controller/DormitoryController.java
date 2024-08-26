package com.test.trainingprogrambackend.controller;

import com.test.trainingprogrambackend.Service.DormitoryService;
import com.test.trainingprogrambackend.entity.Dormitory;
import com.test.trainingprogrambackend.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Dictionary;
import java.util.List;

@RestController
@Api(tags = "分配宿舍")
@RequestMapping("/dor")
public class DormitoryController {
    @Autowired
    private DormitoryService dormitoryService;

    // 后台管理系统接口
    @GetMapping("/assignDor")
    @ApiOperation("后台分配宿舍并返回分配结果")
    public List<Dormitory> assignDor(){
        return dormitoryService.assignDor();
    }

    @GetMapping("/getNotFullDorByClasses")
    @ApiOperation("后台获取对应班级未满宿舍信息")
    public List<Dormitory> getNotFullDorByClasses(String classes){
        return dormitoryService.findNotFullDorByClasses(classes);
    }

    @GetMapping("/refreshDor")
    @ApiOperation("后台刷新宿舍信息")
    public List<Dormitory> refreshDor(){
        return dormitoryService.refreshDor();
    }

    @PostMapping("/adjustByNotFullDor")
    @ApiOperation("后台调整学生至非满宿舍")
    public Result adjustByNotNullDor(String studentid, String dorName){
        dormitoryService.adjustDorByNotFullDor(studentid, dorName);
        return Result.ok().message("调整成功");
    }

    @PostMapping("/adjustByExchange")
    @ApiOperation("后台交换学生宿舍")
    public Result adjustByExchange(String studentid1, String studentid2) {
        dormitoryService.adjustDorByExchange(studentid1, studentid2);
        return Result.ok().message("交换成功");
    }
    // 客户端接口

    @ApiOperation("学生是否申请宿舍")
    @PostMapping("/isApply")
    public Result isApply(String studentid, int dorStatus){
        return dormitoryService.isApplyDor(studentid, dorStatus) == 1 ? Result.ok().message("选择是否申请成功") : Result.error().message("选择是否申请成功失败");
    }

    @PostMapping("/viewDor")
    @ApiOperation("学生查看宿舍信息")
    public Dormitory viewDor(String studentid){
        return dormitoryService.findDorByStudentId(studentid);
    }
}
