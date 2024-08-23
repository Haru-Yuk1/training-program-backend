package com.test.trainingprogrambackend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_student")
public class Student {
    private int id;
    private String stu_name;
    private String idcard;
    private String college;
    private String major;
    private String classes;
    private String stu_id;
    private String phone;
    private String nation;
    private String address;
    private String password;
    private String image;
    private String email;
    private int status;


}
