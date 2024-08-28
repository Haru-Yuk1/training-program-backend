package com.test.trainingprogrambackend.entity;

import lombok.Data;

@Data
public class Section {
    private String classNumber;
    private String classroom;
    private short week;
    private String day;
    private String timeId;
}
