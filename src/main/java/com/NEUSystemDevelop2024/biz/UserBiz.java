package com.NEUSystemDevelop2024.biz;

import com.NEUSystemDevelop2024.entity.User;
import com.NEUSystemDevelop2024.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBiz {
    @Autowired
    private UserMapper mapper;

    public List<User> getUserList(){ return mapper.listUsers();}
}
