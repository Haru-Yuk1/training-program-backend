package com.test.trainingprogrambackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;


import com.test.trainingprogrambackend.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
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

    @Update("update student set dorName = #{dorName} where id = #{id}")
    void updateDorName(@Param("id") int id, @Param("dorName") String dorName);

    //通过idCard找学生
    @Select("select * from student where idCard=#{idCard}")
    Student findByIdCard(@Param("idCard") String idCard);

    //通过idCard找到学生激活账户
    @Update("update student set password=#{password},email=#{email},status=#{status} where idCard=#{idCard}")
    int register(@Param("idCard") String idCard,@Param("email") String email, @Param("password") String password, @Param("status") int status);

    //用手机注册
    @Update("update student set password=#{password},status=#{status} where phone=#{phone}")
    int registerByPhone(@Param("phone") String phone,@Param("password") String password,int status);

    @Update("update student set password=#{password},status=#{status} where email=#{email}")
    int registerByEmail(@Param("email") String email,@Param("password") String password,int status);


    //用手机登录
    @Select("select * from student where phone=#{phone} and password=#{password}")
    Student loginByPhone(@Param("phone") String phone, @Param("password") String password);

    @Select("select * from student where email=#{email} and password=#{password}")
    Student loginByEmail(@Param("email") String email, @Param("password") String password);

    //更新学生的头像图片地址
    @Update("update student set imgUrl=#{imgUrl}")
    int updateImgUrl(@Param("imgUrl") String imgUrl);


//    @Update("update student set name=#{name},idCard=#{idCard},major=#{major},classes=#{classes},studentid=#{studentid},nation=#{nation},address=#{address} where phone=#{phone}")
//    int updateInformation(String name,String idCard,String major,String classes, String studentid,String nation,String address,String phone);


//    @Update("update student set name=#{name},nation=#{nation},gender=#{gender},birthday=#{birthday},idCard=#{idCard},major=#{major},classes=#{classes},studentid=#{studentid},address=#{address},grade=#{grade} where phone=#{phone}")
//    int updateInformation();


//    @Update("update student set stu_name=#{stu_name},idcard=#{idcard},college=#{college},major=#{major},classes=#{classes},stu_id=#{stu_id},nation=#{nation},address=#{address} where email=#{email}")
//    int updateInformationByEmail(String stu_name,String idcard,String college,String major,String classes, String stu_id,String nation,String address,String email);



}
