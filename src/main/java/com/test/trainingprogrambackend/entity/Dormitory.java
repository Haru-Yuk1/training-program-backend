package com.test.trainingprogrambackend.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("这是Dormitory实体类")
public class Dormitory {
    @TableId("dorName")
    private String dorName;
    private String classes;
    private String deptName;

}
