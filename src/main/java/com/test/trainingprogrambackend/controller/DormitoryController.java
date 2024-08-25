package com.test.trainingprogrambackend.controller;

import com.test.trainingprogrambackend.Service.DormitoryService;
import com.test.trainingprogrambackend.entity.Dormitory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "分配宿舍")
public class DormitoryController {
    @Autowired
    private DormitoryService dormitoryService;

    @GetMapping("/assignDor")
    @ApiOperation("分配宿舍")
    public List<Dormitory> assignDor(){
        return dormitoryService.assignDor();
    }
}
