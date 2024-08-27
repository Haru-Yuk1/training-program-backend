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
    @Insert("INSERT INTO User (userName, phone, peoName, role, status, createDate) " +
            "VALUES (#{userName}, #{phone}, #{peoName}, #{role}, #{status}, #{createDate})")
    int insert(User user);

    //修改用户
    @UpdateProvider(type = SqlProvider.class, method = "updateUser")
    int update(@Param("user") User user);


    class SqlProvider {
        public String updateUser(@Param("user") User user) {
            return new SQL() {{
                UPDATE("User");
                if (user.getUserName() != null && !user.getUserName().isEmpty()) {
                    SET("userName = #{user.userName}");
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
                WHERE("userName = #{user.originalUserName}");
            }}.toString();
        }
    }

    //删除用户
    @Delete("DELETE FROM User WHERE userName = #{userName}")
    int delete(@Param("userName") String userName);

    @Select("select userName, phone, peoName, role, status, createDate from user")
    public List<User> query();

    //用于检测权限和状态
    @Select("select role, status from user where userName = #{userName}")
    public User queryRoleAndStatus(@Param("userName") String userName);

}
