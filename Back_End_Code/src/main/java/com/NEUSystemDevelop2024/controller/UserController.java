package com.NEUSystemDevelop2024.controller;

import com.NEUSystemDevelop2024.biz.UserBiz;
import com.NEUSystemDevelop2024.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserBiz userBiz;

    @RequestMapping("/login")
    public Map login(User user, HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        user = userBiz.checkLogin(user);
        Map map = new HashMap();
        if(user != null){
            session.setAttribute("login_user",user);
            map.put("isOk",true);
            map.put("user",user);
            map.put("msg","登录成功");
            return map;
        }else{
            map.put("isOk",false);
            map.put("user", null);
            map.put("msg","登录失败");
            return map;
        }

    }
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
