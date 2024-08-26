package com.test.trainingprogrambackend.Service;

import com.test.trainingprogrambackend.entity.Takes;
import com.test.trainingprogrambackend.mapper.CourseMapper;
import com.test.trainingprogrambackend.mapper.StudentMapper;
import com.test.trainingprogrambackend.mapper.TakesMapper;
import com.test.trainingprogrambackend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class TakesService {
    @Autowired
    private TakesMapper takesMapper;

    @Autowired
    private StudentMapper studentMapper;

    public Result takesOperation(String studentid, String classNumber){
        if(takesMapper.findByClassNumber(classNumber)==null){
            return Result.error().message("错误课程码");
        }
        if(studentMapper.findByStudentid(studentid)==null){
            return Result.error().message("学生学号id错误");
        }

        Map<String, Object> courseClass = takesMapper.isClassFull(classNumber);

        Long selectedNumLong = (Long) courseClass.get("selectedNumber");
        Long capacityLong = (Long) courseClass.get("capacity");
        int isFull = (int) courseClass.get("isFull");

        int selectedNum = selectedNumLong.intValue();
        int capacity = capacityLong.intValue();

        System.out.println(selectedNum+" "+capacity+" "+isFull);
        if(isFull==1){
            return Result.error().message("课程已选满");
        }
        else{
            List<Takes> takes=takesMapper.findByStudentid(studentid);

            if(!takes.isEmpty()){
                for(Takes t:takes){
                    if(t.getClassNumber().equals(classNumber)){
                        return Result.error().message("你已选择课程");
                    }
                }
            }


            takesMapper.insertTakes(studentid,classNumber);
            takesMapper.updateCourseClass(classNumber,selectedNum+1);
            if(selectedNum+1>=capacity){
                takesMapper.updateFull(classNumber,1);

            }
            return Result.ok().message("选课成功");
        }
    }
    public Result deleteTakesOperation(String studentid, String classNumber){
        Map<String, Object> courseClass = takesMapper.isClassFull(classNumber);

        Long selectedNumLong = (Long) courseClass.get("selectedNumber");
        Long capacityLong = (Long) courseClass.get("capacity");
        int isFull = (int) courseClass.get("isFull");

        int selectedNum = selectedNumLong.intValue();
        int capacity = capacityLong.intValue();


        List<Takes> takes=takesMapper.findByStudentid(studentid);
        System.out.println(takes);

        if(takes.isEmpty()){
            return Result.error().message("你未选该课程");
        }
        if(selectedNum-1<0){
            return Result.error().message("所选人数为负，");
        }
        takesMapper.deleteByStudentid(studentid,classNumber);
        takesMapper.updateCourseClass(classNumber,selectedNum-1);
        if(selectedNum-1<capacity){
            takesMapper.updateFull(classNumber,0);

        }
        return Result.ok().message("退课成功");
    }


}
