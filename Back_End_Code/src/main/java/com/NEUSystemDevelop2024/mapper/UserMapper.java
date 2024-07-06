package com.NEUSystemDevelop2024.mapper;

import com.NEUSystemDevelop2024.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from t_user")
    List<User> listUsers();

    @Select("select * from t_user where userName = #{userName}")
    public User seekUserByUserName(String name);

    @Insert("INSERT INTO t_user (companyId,userName, realName, gender, password, enabled, career, role, phoneNumber, email, createTime, description) " +
            "VALUES (#{companyId},#{userName}, #{realName}, #{gender}, #{password}, #{enabled}, #{career}, #{role}, #{phoneNumber}, #{email}, #{createTime}, #{description})")
    Integer insertUser(User user);



    @Insert("INSERT INTO t_user (companyId,departmentId, userName, realName, gender, password, enabled, career, role, phoneNumber, email, createTime, description) " +
            "VALUES (#{companyId},#{departmentId},#{userName}, #{realName}, #{gender}, #{password}, #{enabled}, #{career}, #{role}, #{phoneNumber}, #{email}, #{createTime}, #{description})")
    Integer insertUserByPage(User user);

    @Delete("delete from t_user where UserId=#{id}")
    Integer deleteUserById(Integer id);

    @Update("UPDATE t_user SET " +
            "departmentId = #{departmentId}, " +
            "userName = #{userName}, " +
            "gender = #{gender}, " +
            "enabled = #{enabled}, " +
            "career = #{career}, " +
            "role = #{role}, " +
            "phoneNumber = #{phoneNumber}, " +
            "email = #{email}, " +
            "description = #{description} " +
            "WHERE userId = #{userId}")
    Integer updateUserByPage(User user);
}
