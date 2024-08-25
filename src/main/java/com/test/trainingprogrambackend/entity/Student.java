package com.test.trainingprogrambackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.apache.ibatis.annotations.Param;

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
    private String idCard;
    private String major;
    private String grade;
    private String classes;
    private String address;
    private String studentid;
    private String password;
    private String dorName;
    private String deptName;
    private int perference;
    private int status;
    private int isFinishSelect;
    private int dorStatus;
    private int isFinishLog;


}
