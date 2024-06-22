package com.NEUSystemDevelop2024.controller;

import com.NEUSystemDevelop2024.biz.UserBiz;
import com.NEUSystemDevelop2024.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserBiz userBiz;

    @RequestMapping("/list")
    public Map list()
    {
        List<User> userList = userBiz.getUserList();
        Map map = new HashMap();
        map.put("isOk", true);
        map.put("users", userList);
        map.put("msg","查询成功");
        return map;
    }

}
