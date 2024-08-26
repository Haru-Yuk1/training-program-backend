package com.test.trainingprogrambackend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private int id;
    private String title;
    private String content;
    private Date publishDate;

    @TableField(exist = false)
    private String originalTitle;
}
