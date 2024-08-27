package com.test.trainingprogrambackend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@TableName("student")
@ApiModel("这是Student实体类")
public class Student {

    private int id;
    private String name;
    private String nation;
    private String gender;
    private String birthday;
    private String phone;
    private String email;
    @TableField("idCard")
    private String idCard;
    private String major;
    private String classes;
    private String address;
    @TableField("studentid")
    private String studentid;
    private String password;
    @TableField("dorName")
    private String dorName;
    @TableField("depName")
    private String deptName;
    private int preference;
    private int status;
    @TableField("isFinishSelect")
    private int isFinishSelect;
    @TableField("dorStatus")
    private int dorStatus;
    @TableField("isFinishLog")
    private int isFinishLog;
    @TableField("imageUrl")
    private String imageUrl;

    @TableField(exist = false)
    private String originalStudentId;//前端传来的修改前学号
}
