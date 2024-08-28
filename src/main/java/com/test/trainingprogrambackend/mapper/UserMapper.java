package com.test.trainingprogrambackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.trainingprogrambackend.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    //基础功能
    //添加用户
    @Insert("INSERT INTO User (userId, phone, peoName, role, status, createDate) " +
            "VALUES (#{userId}, #{phone}, #{peoName}, #{role}, #{status}, #{createDate})")
    int insert(User user);

    //修改用户
    @UpdateProvider(type = SqlProvider.class, method = "updateUser")
    int update(@Param("user") User user);


    class SqlProvider {
        public String updateUser(@Param("user") User user) {
            return new SQL() {{
                UPDATE("User");
                if (user.getUserId() != null && !user.getUserId().isEmpty()) {
                    SET("userId = #{user.userId}");
                }
                if (user.getPhone() != null && !user.getPhone().isEmpty()) {
                    SET("phone = #{user.phone}");
                }
                if (user.getPeoName() != null && !user.getPeoName().isEmpty()) {
                    SET("peoName = #{user.peoName}");
                }
                if (user.getRole() != null && !user.getRole().isEmpty()) {
                    SET("role = #{user.role}");
                }
                if (user.getStatus() != null && !user.getStatus().isEmpty()) {
                    SET("status = #{user.status}");
                }
                if (user.getCreateDate() != null) {
                    SET("createDate = #{user.createDate}");
                }
                WHERE("userId = #{user.originalUserId}");
            }}.toString();
        }
    }

    //删除用户
    @Delete("DELETE FROM User WHERE userId = #{userId}")
    int delete(@Param("userId") String userId);

    @Select("select userId, phone, peoName, role, status, createDate from user")
    public List<User> query();

    //用于检测权限和状态
    @Select("select role, status from user where userId = #{userId}")
    public User queryRoleAndStatus(@Param("userId") String userId);


    //登录操作

    //查找
    @Select("select * from user where userId=#{userId} ")
    int selectByUserid(@Param("userId") String userId);

    @Select("select * from user where userId=#{userId} and password=#{password}")
    User LoginUser(@Param("userId") String userId, @Param("password") String password);

    @Select("select * from user where phone=#{phone}")
    User selectByPhone(@Param("phone") String phone);

    // 忘记密码 通过手机
    @Update("update user set password=#{password} where phone=#{phone}")
    int updatePasswordByPhone(@Param("phone") String phone, @Param("password") String password);
}
