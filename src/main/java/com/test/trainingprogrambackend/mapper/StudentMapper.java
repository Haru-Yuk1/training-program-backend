package com.test.trainingprogrambackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.test.trainingprogrambackend.entity.Student;
import org.apache.ibatis.annotations.*;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
//    @Select("select * from t_student")
//    List<Student> findAll();
    @Insert("insert into t_student (phone,password,status) values (#{phone},#{password},#{status})")
    int registerByPhone(@Param("phone") String phone,@Param("password") String password,int status);

    @Insert("insert into t_student (email,password,status) values (#{email},#{password},#{status})")
    int registerByEmail(@Param("email") String email,@Param("password") String password,int status);

    @Select("select * from t_student where phone=#{phone} and password=#{password}")
    Student loginByPhone(@Param("phone") String phone, @Param("password") String password);

    @Select("select * from t_student where email=#{email} and password=#{password}")
    Student loginByEmail(@Param("email") String email, @Param("password") String password);

    @Update("update t_student set stu_name=#{stu_name},idcard=#{idcard},college=#{college},major=#{major},classes=#{classes},stu_id=#{stu_id},nation=#{nation},address=#{address} where phone=#{phone}")
    int updateInformationByPhone(String stu_name,String idcard,String college,String major,String classes, String stu_id,String nation,String address,String phone);

    @Update("update t_student set stu_name=#{stu_name},idcard=#{idcard},college=#{college},major=#{major},classes=#{classes},stu_id=#{stu_id},nation=#{nation},address=#{address} where email=#{email}")
    int updateInformationByEmail(String stu_name,String idcard,String college,String major,String classes, String stu_id,String nation,String address,String email);



}
