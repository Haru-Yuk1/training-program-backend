package com.test.trainingprogrambackend.controller;

import com.test.trainingprogrambackend.Service.DormitoryService;
import com.test.trainingprogrambackend.entity.Dormitory;
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

    @GetMapping("/assignDor")
    @ApiOperation("分配宿舍并返回分配结果")
    public List<Dormitory> assignDor(){
        return dormitoryService.assignDor();
    }

    @GetMapping("/getNotFullDorByClasses")
    @ApiOperation("获取对应班级未满宿舍信息")
    public List<Dormitory> getNotFullDorByClasses(String classes){
        return dormitoryService.findNotFullDorByClasses(classes);
    }

    @GetMapping("/refreshDor")
    @ApiOperation("刷新宿舍信息")
    public List<Dormitory> refreshDor(){
        return dormitoryService.refreshDor();
    }

    @PostMapping("/adjustByNotFullDor")
    @ApiOperation("调整学生至非满宿舍")
    public void adjustByNotNullDor(String studentid, String dorName){
        dormitoryService.adjustDorByNotFullDor(studentid, dorName);
    }

    @PostMapping("/adjustByExchange")
    @ApiOperation("交换学生宿舍")
    public void adjustByExchange(String studentid1, String studentid2){
        dormitoryService.adjustDorByExchange(studentid1, studentid2);
    }
}
