package com.test.trainingprogrambackend.controller;

import com.test.trainingprogrambackend.Service.DormitoryService;
import com.test.trainingprogrambackend.entity.Dormitory;
import com.test.trainingprogrambackend.entity.Student;
import com.test.trainingprogrambackend.mapper.StudentMapper;
import com.test.trainingprogrambackend.util.JwtUtils;
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

    @Autowired
    private StudentMapper studentMapper;



    // 客户端接口
    @ApiOperation("客户端学生是否申请宿舍")
    @PostMapping("/isApply")
    public Result isApply(@RequestHeader("Authorization") String token, @RequestParam int dorStatus){
        String StudentIdCard= JwtUtils.getClaimsByToken(token).getSubject();
        Student student=studentMapper.findByIdCard(StudentIdCard);
        return dormitoryService.isApplyDor(student.getStudentid(), dorStatus) == 1 ? Result.ok().message("选择是否申请成功") : Result.error().message("选择是否申请成功失败");
    }

    @ApiOperation("客户端学生查看宿舍信息")
    @GetMapping("/viewDor")
    public Dormitory viewDor(@RequestHeader("Authorization") String token){
        String StudentIdCard= JwtUtils.getClaimsByToken(token).getSubject();
        Student student=studentMapper.findByIdCard(StudentIdCard);
        return dormitoryService.findDorByStudentId(student.getStudentid());
    }

    @ApiOperation("接受学生偏好信息")
    @PostMapping("/acceptPreference")
    public Result acceptPreference(@RequestHeader("Authorization") String token, @RequestParam String preference){
        String StudentIdCard= JwtUtils.getClaimsByToken(token).getSubject();
        Student student=studentMapper.findByIdCard(StudentIdCard);
        return dormitoryService.updateStudentPreference(student.getStudentid(), preference) == 1 ? Result.ok().message("接受偏好成功") : Result.error().message("接受偏好失败");
    }
}
