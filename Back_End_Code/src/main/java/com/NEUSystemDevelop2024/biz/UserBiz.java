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

    public User checkLogin(User user){
        User dbUser = mapper.seekUserByUserName(user.getUserName());
        if(dbUser != null && user.getPassword().equals(dbUser.getPassword()))
        {
            dbUser.setPassword(null);
            return dbUser;
        }
        else return null;
    }
    public List<User> getUserList(){ return mapper.listUsers();}

    public User seekUserByUserName(String userName){
        User user = mapper.seekUserByUserName(userName);
        return user;
    }


    public void addUser(User user) {
        mapper.insertUser(user);
    }
}
