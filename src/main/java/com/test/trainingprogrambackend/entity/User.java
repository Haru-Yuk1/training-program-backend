package com.test.trainingprogrambackend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    private int id;
    private String userId;
    private String password;
    private String phone;
    private String peoName;
    private String role;
    private String status;
    private Date createDate;

    @TableField(exist = false)
    private String originalUserId; // 原始工号
}
