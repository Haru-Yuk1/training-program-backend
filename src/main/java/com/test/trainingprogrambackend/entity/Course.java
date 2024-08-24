package com.test.trainingprogrambackend.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("这是Course实体类")
public class Course {
    @TableId("code")
    private char code;
    private String name;
    private Integer credit;
    private String type;
    private String deptName;
}
