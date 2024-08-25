package com.test.trainingprogrambackend.Service;

import com.test.trainingprogrambackend.entity.Dormitory;
import com.test.trainingprogrambackend.entity.Student;
import com.test.trainingprogrambackend.mapper.DormitoryMapper;
import com.test.trainingprogrambackend.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DormitoryService {
    @Autowired
    private DormitoryMapper dormitoryMapper;

    @Autowired
    private StudentMapper studentMapper;

    public List<Dormitory> assignDor() {
        List<Student> studentList = studentMapper.findClassesAndDorStatus();
        for (Student student : studentList) {
            if (student.getDorStatus() == 1) {
                //分配宿舍
                // 获取对应班级未满宿舍
                Dormitory dormitory = dormitoryMapper.getByClassesNotNull(student.getClasses());
                // 更新学生宿舍信息
                studentMapper.updateDorName(student.getId(), dormitory.getDorName());
                // 更新宿舍人数
                dormitoryMapper.updatePeoNumber(dormitory.getDorName());
                if (dormitory.getPeoNumber() + 1 == 4) {
                    dormitoryMapper.updateIsFull(dormitory.getDorName());
                }
            }
        }
        return dormitoryMapper.getDormitoryWithStudent();
    }
}
