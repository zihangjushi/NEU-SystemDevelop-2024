package com.NEUSystemDevelop2024.mapper;

import com.NEUSystemDevelop2024.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from t_user")
    List<User> listUsers();

    @Select("select * from t_user where userName = #{userName}")
    public User seekUserByUserName(String name);

    @Insert("INSERT INTO t_user (companyId,userName, realName, gender, password, enabled, career, role, phoneNumber, email, createTime, description) " +
            "VALUES (#{companyId},#{userName}, #{realName}, #{gender}, #{password}, #{enabled}, #{career}, #{role}, #{phoneNumber}, #{email}, #{createTime}, #{description})")
    void insertUser(User user);

}
