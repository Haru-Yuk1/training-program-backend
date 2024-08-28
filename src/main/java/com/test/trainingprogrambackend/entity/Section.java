package com.test.trainingprogrambackend.entity;

import lombok.Data;

@Data
public class Section {
    private String classNumber;
    private String classroom;
    private String day;
    private int timeId;
    private int week;
}
