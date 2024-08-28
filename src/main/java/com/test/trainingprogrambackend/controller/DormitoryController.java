package com.test.trainingprogrambackend.controller;

import com.test.trainingprogrambackend.Service.DormitoryService;
import com.test.trainingprogrambackend.entity.Dormitory;
import com.test.trainingprogrambackend.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "分配宿舍")
@RequestMapping("/dor")
public class DormitoryController {
    @Autowired
    private DormitoryService dormitoryService;

    // 客户端接口
    @ApiOperation("客户端学生是否申请宿舍")
    @PostMapping("/isApply")
    public Result isApply(@RequestParam String studentid, @RequestParam int dorStatus){
        return dormitoryService.isApplyDor(studentid, dorStatus) == 1 ? Result.ok().message("选择是否申请成功") : Result.error().message("选择是否申请成功失败");
    }

    @ApiOperation("客户端学生查看宿舍信息")
    @GetMapping("/viewDor")
    public Dormitory viewDor(@RequestParam String studentid){
        return dormitoryService.findDorByStudentId(studentid);
    }

    @ApiOperation("接受学生偏好信息")
    @PostMapping("/acceptPreference")
    public Result acceptPreference(@RequestParam String studentid, @RequestParam String preference){
        return dormitoryService.updateStudentPreference(studentid, preference) == 1 ? Result.ok().message("接受偏好成功") : Result.error().message("接受偏好失败");
    }
}
