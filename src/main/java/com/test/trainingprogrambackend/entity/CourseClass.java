package com.test.trainingprogrambackend.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;
import lombok.Data;

@Data
@ApiModel("这是CourseClass实体类")
public class CourseClass {
    @TableId("classNumber")
    private String classNumber;
    private String teacherName;
    private Integer capacity;
    private Integer selectedNumber;
    private Integer isFull;
    private String code;

}
