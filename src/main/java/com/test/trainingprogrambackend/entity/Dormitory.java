package com.test.trainingprogrambackend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@TableName("student")
@ApiModel("这是Dormitory实体类")
public class Dormitory {
    @TableId("dorName")
    private String dorName;
    private String classes;
    private String deptName;
    private short peoNumber;
    private short isFull;
    private String areaName;
    private String gender;

    @TableField(exist = false)
    private List<Student> students;//学生姓名
}
