package com.test.trainingprogrambackend.controller;

import com.test.trainingprogrambackend.entity.StudentStatistics;
import com.test.trainingprogrambackend.mapper.DormitoryMapper;
import com.test.trainingprogrambackend.mapper.StudentMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags="后台管理系统数据")
@RequestMapping("/backstage")
public class BackstageController {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private DormitoryMapper dormitoryMapper;

    //返回完成账号激活、选课总人数、选择申请宿舍、信息录入、注册以及总学生数，用于进度展示
    @GetMapping("/process")
    @ApiOperation("返回完成账号激活、选课总人数、选择申请宿舍、信息录入、注册以及总学生数")
    public StudentStatistics registrationNum() {
        return studentMapper.findAllStatistics();
    }

    // 用于中心旭日图，各班级完成注册人数、各专业实际人数、各学院实际人数
    @ApiOperation("用于中心旭日图，各班级完成注册人数、各专业实际人数、各学院实际人数")
    @GetMapping("/sunChart")
    public Map<String, Object> sunChartNum() {
        Map<String, Object> statSumChat = new HashMap<>();
        statSumChat.put("registeredNumByClass", studentMapper.countRegisteredNumByClass());
        statSumChat.put("studentByMajor", studentMapper.countStudentsByMajor());
        statSumChat.put("studentByDept", studentMapper.countStudentsByDept());
        return statSumChat;
    }


    // 用于宿舍情况展示，各园区床位总数和入住人数统计情况
    @ApiOperation("用于宿舍情况展示，各园区床位总数和入住人数统计情况")
    @GetMapping("/dorSituation")
    public Map<String, Object> dorSituation() {
        Map<String, Object> dorBedNum = new HashMap<>();
        dorBedNum.put("totalBedsByArea", dormitoryMapper.countTotalBedsByArea());
        dorBedNum.put("totalResidentsByArea", dormitoryMapper.countTotalResidentsByArea());
        return dorBedNum;
    }

    // 用于文字展示，床位总数和申请住宿、不申请住宿人数
    @ApiOperation("用于文字展示，床位总数和申请住宿、不申请住宿人数")
    @GetMapping("/bedApply")
    public Map<String, Object> bedApplyNum() {
        Map<String, Object> isApplyNum = new HashMap<>();
        isApplyNum.put("totalBeds", dormitoryMapper.countTotalBeds());
        isApplyNum.put("applyDorm", studentMapper.countApplyDorm());
        isApplyNum.put("notApplyDorm", studentMapper.countNotApplyDorm());
        return isApplyNum;
    }
}
