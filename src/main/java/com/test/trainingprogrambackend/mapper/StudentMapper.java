package com.test.trainingprogrambackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;


import com.test.trainingprogrambackend.entity.Student;
import com.test.trainingprogrambackend.entity.StudentStatistics;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    // 后台管理操作
    @Select("select " +
    "sum(case when status=1 then 1 else 0 end) as ActivateNum," +
       "sum(case when isFinishSelect=1 then 1 else 0 end) as chooseNum," +
          "sum(case when dorStatus!=0 then 1 else 0 end) as dorFinishNum, " +
            "sum(case when isFinishLog=1 then 1 else 0 end) as logFinishNum, " +
                "sum(case when isFinishLog=1 && dorStatus=1 && isFinishSelect=1 then 1 else 0 end) as registerFinishNum, " +
                "count(*) as totalCount " + "from student")
    StudentStatistics findAllStatistics();

    // 统计申请住宿的人数
    @Select("SELECT COUNT(*) AS applyDorm FROM student WHERE dorStatus = 1")
    int countApplyDorm();

    // 统计不申请住宿的人数
    @Select("SELECT COUNT(*) AS notApplyDorm FROM student WHERE dorStatus = 2")
    int countNotApplyDorm();

    // 统计各班级完成注册人数
    @Select("SELECT classes, COUNT(*) AS registeredCount " +
            "FROM student " +
            "WHERE status = 1 AND isFinishSelect = 1 AND dorStatus IN (1, 2) AND isFinishLog = 1 " +
            "GROUP BY classes")
    List<Map<String, Object>> countRegisteredNumByClass();

    // 统计各专业实际人数
    @Select("SELECT major, COUNT(*) AS studentCount " +
            "FROM student " +
            "GROUP BY major")
    List<Map<String, Object>> countStudentsByMajor();

    // 统计各学院实际人数
    @Select("SELECT deptName, COUNT(*) AS studentCount " +
            "FROM student " +
            "GROUP BY deptName")
    List<Map<String, Object>> countStudentsByDept();

    //获取所有学生
    @Select("select * from student")
    List<Student> findAll();

    //通过学生名字获取学生信息
    @Select("select * from student where name=#{name}")
    Student findByName(String name);

    @Select("select classes, dorStatus from student")
    List<Student> findClassesAndDorStatus();

    @Select("select * from student where dorName=#{dorName}")
    List<Student> findAllByDorName();

    @Select("select dorName from student where studentid=#{studentid}")
    String findDorNameById(String studentid);

    @Update("update student set dorName = #{dorName} where studentid = #{studentid}")
    int updateDorNameById(@Param("studentid") String studentid, @Param("dorName") String dorName);

    @Update("update student set dorStatus = #{dorStatus} where studentid = #{studentid}")
    int updateDorStatusById(@Param("studentid") String studentid, @Param("dorStatus") int dorStatus);

    // 登录操作

    //通过idCard找学生
    @Select("select * from student where idCard=#{idCard}")
    Student findByIdCard(@Param("idCard") String idCard);
    @Select("select * from student where studentid=#{studentid}")
    Student findByStudentid(String studentid);

    //通过idCard找到学生激活账户
    @Update("update student set password=#{password},email=#{email},status=#{status} where idCard=#{idCard}")
    int registerByEmail(@Param("idCard") String idCard,@Param("email") String email, @Param("password") String password, @Param("status") int status);

    //用手机注册
    @Update("update student set password=#{password},phone=#{phone},status=#{status} where idCard=#{idCard}")
    int registerByPhone(@Param("idCard") String idCard,@Param("phone") String phone, @Param("password") String password, @Param("status") int status);

    @Select("select * from student where phone=#{phone} and password=#{password}")
    Student loginByPhoneAndPassword(@Param("phone") String phone, @Param("password") String password);

    @Select("select * from student where email=#{email} and password=#{password}")
    Student loginByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    @Select("select * from student where phone=#{phone}")
    Student loginByPhoneAndCode(@Param("phone") String phone);

    @Select("select * from student where email=#{email}")
    Student loginByEmailAndCode(@Param("email") String email);


    @Update("update student set imageUrl=#{imageUrl} where studentid=#{studentid}")
    int updateImageUrl(@Param("imageUrl") String imageUrl,String studentid);





}
