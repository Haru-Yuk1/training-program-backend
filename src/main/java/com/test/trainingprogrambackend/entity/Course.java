package com.test.trainingprogrambackend.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("这是Course实体类")
public class Course {
    @TableId("code")
    private String code;
    private String name;
    private Float credit;
    private String type;
    private String deptName;
}
