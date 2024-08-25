package com.test.trainingprogrambackend.Service;

import com.test.trainingprogrambackend.entity.Takes;
import com.test.trainingprogrambackend.mapper.TakesMapper;
import com.test.trainingprogrambackend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TakesService {
    @Autowired
    private TakesMapper takesMapper;


    public Result takesOperation(String studentid, String classNumber){
        Map<String, Object> courseClass = takesMapper.isClassFull(classNumber);
        int selectedNum = (int) courseClass.get("selectedNumber");
        int capacity = (int) courseClass.get("capacity");
        int isFull = (int) courseClass.get("isFull");
        System.out.println(selectedNum+" "+capacity+" "+isFull);
        if(isFull==1){
            return Result.error().message("课程已选满");
        }
        else{
            List<Takes> takes=takesMapper.findAll();
            for(Takes t:takes){
                if(t.getClassNumber().equals(classNumber)&& t.getStudentid().equals(studentid)){
                    return Result.error().message("你已选择课程");
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


}
