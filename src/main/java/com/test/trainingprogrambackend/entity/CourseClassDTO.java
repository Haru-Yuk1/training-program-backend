package com.test.trainingprogrambackend.entity;

import lombok.Data;

@Data
public class CourseClassDTO {
    private char code;
    private String name;
    private Integer credit;
    private String type;
    private String deptName;
    private String classNumber;
    private String teacherName;
    private Integer capacity;
    private Integer selectedNumber;
    private Integer isFull;

}
