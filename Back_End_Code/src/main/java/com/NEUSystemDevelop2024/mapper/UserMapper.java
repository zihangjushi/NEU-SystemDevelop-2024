package com.NEUSystemDevelop2024.mapper;

import com.NEUSystemDevelop2024.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from t_user")
    List<User> listUsers();

    @Select("select * from t_user where userName = #{userName}")
    public User seekUserByUserName(String name);


}
