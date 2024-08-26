package com.test.trainingprogrambackend.Service;

import com.test.trainingprogrambackend.entity.Dormitory;
import com.test.trainingprogrambackend.entity.Student;
import com.test.trainingprogrambackend.mapper.DormitoryMapper;
import com.test.trainingprogrambackend.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DormitoryService {
    @Autowired
    private DormitoryMapper dormitoryMapper;

    @Autowired
    private StudentMapper studentMapper;

    // 后台管理系统操作
        // 分配宿舍操作
    public List<Dormitory> assignDor() {
        List<Student> studentList = studentMapper.findClassesAndDorStatus();
        for (Student student : studentList) {
            if (student.getDorStatus() == 1) {
                //分配宿舍
                // 获取对应班级未满宿舍
                Dormitory dormitory = dormitoryMapper.getByClassesNotNullOne(student.getClasses());
                // 更新学生宿舍信息
                studentMapper.updateDorNameById(student.getStudentid(), dormitory.getDorName());
                // 更新宿舍人数
                dormitoryMapper.updatePeoNumberAdd(dormitory.getDorName());
                if (dormitory.getPeoNumber() + 1 == 4) {
                    dormitoryMapper.updateFull(dormitory.getDorName());
                }
            }
        }
        return dormitoryMapper.getDormitoryWithStudent();
    }

        // 调整宿舍涉及操作
            // 查询对应班级未满宿舍
    public List<Dormitory> findNotFullDorByClasses(String classes) {
        return dormitoryMapper.getByClassesNotNullAll(classes);
    }

            // 刷新宿舍显示信息
    public List<Dormitory> refreshDor() {
        return dormitoryMapper.getDormitoryWithStudent();
    }

            // 调整宿舍操作
    public int adjustDorByNotFullDor(String studentid, String dorName) {
        String dorNameFromStudent = studentMapper.findDorNameById(studentid);
        int correct = 1;
        correct = studentMapper.updateDorNameById(studentid, dorName) == 1 ? correct : 0;
        if(dormitoryMapper.getpeoNumberByDorName(dorNameFromStudent).getPeoNumber() == 4) {
            correct = dormitoryMapper.updateNotFull(dorNameFromStudent) == 1 ? correct : 0;
        }
        correct = correct == dormitoryMapper.updatePeoNumberMinus(dorNameFromStudent) ? 1 : 0;
        if(dormitoryMapper.getpeoNumberByDorName(dorName).getPeoNumber() + 1 == 4) {
            correct = dormitoryMapper.updateFull(dorName) == 1 ? correct : 0;
        }
        correct = dormitoryMapper.updatePeoNumberAdd(dorName) == 1 ? correct : 0;
        return correct;
    }

    public int adjustDorByExchange(String studentid1, String studentid2) {
        String dorName1 = studentMapper.findDorNameById(studentid1);
        String dorName2 = studentMapper.findDorNameById(studentid2);
        return studentMapper.updateDorNameById(studentid1, dorName2) & studentMapper.updateDorNameById(studentid2, dorName1);
    }

    // 客户端操作
    public int isApplyDor(String studentid, int dorStatus) {
        return studentMapper.updateDorStatusById(studentid, dorStatus);
    }

    public Dormitory findDorByStudentId(String studentid) {
        return dormitoryMapper.getByDorNameWithStudent(studentMapper.findDorNameById(studentid));
    }
}
