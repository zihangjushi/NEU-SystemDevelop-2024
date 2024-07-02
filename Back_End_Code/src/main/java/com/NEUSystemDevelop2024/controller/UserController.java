package com.NEUSystemDevelop2024.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.NEUSystemDevelop2024.biz.CompanyBiz;
import com.NEUSystemDevelop2024.biz.UserBiz;
import com.NEUSystemDevelop2024.entity.Company;
import com.NEUSystemDevelop2024.entity.User;
import io.swagger.models.auth.In;
import nonapi.io.github.classgraph.json.JSONUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private CompanyBiz companyBiz;
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

    @RequestMapping("/register")
    public Map register(@RequestBody Map<String, Object> request){
        Map<String, Object> map = new HashMap<>();

        try {
            String name = (String) request.get("realname");
            String usernum = (String) request.get("usernum");
            String password = (String) request.get("password");
            int gender = Integer.parseInt((String) request.get("gender"));
            String description = (String) request.get("description");
            String email = (String) request.get("email");
                String companyName = (String) request.get("newCompanyName");
                String contactName = (String) request.get("contactName");
                String phoneNumber = (String) request.get("contactPhone");
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String createTime = now.format(formatter);

                Company company = new Company(name,1,phoneNumber,contactName,companyName,description,createTime);

                companyBiz.addCompany(company);

                User user = new User(companyBiz.searchByCompanyName(companyName).getCompanyId(),usernum,name,gender,password,"admin",1,"管理员",phoneNumber,email,createTime,null,0);
                userBiz.addUser(user);

            map.put("isOk", true);
            map.put("msg", "注册成功！");
        } catch (Exception e) {
            map.put("isOk", false);
            map.put("msg", "注册失败：" + e.getMessage());
        }
        return map;
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

    @RequestMapping("/searchByUserName")
    public Map searchByUserName(String userName)
    {
        User user = userBiz.seekUserByUserName(userName);
        Map map = new HashMap();
        map.put("isOk", true);
        map.put("user", user);
        map.put("msg","查询成功");
        return map;
    }

    @RequestMapping("/searchByCompanyId")
    public Map searchByCompanyId(Integer companyId)
    {
        List<User> userList = new ArrayList<>();
        List<User> users = new ArrayList<>();
        userList = userBiz.getUserList();

        for(User user : userList)
        {
            if(user.getCompanyId() == companyId) users.add(user);
        }
        Map map = new HashMap();
        map.put("isOk", true);
        map.put("users", users);
        map.put("msg","查询成功");
        return map;
    }

    @RequestMapping("/searchByCompanyInformation")
    public Map searchByCompanyInformation(Integer companyId, Integer departmentId)
    {
        List<User> userList = userBiz.getUserList();
        List<User> users = new ArrayList<>();
        for(User user1 : userList)
        {
            if(user1.getCompanyId() == companyId && user1.getDepartmentId() == departmentId)
            {
                users.add(user1);
            }
        }
        Map map = new HashMap();
        map.put("isOk", true);
        map.put("users", users);
        map.put("msg","查询成功");
        return map;
    }

    @RequestMapping("/add")
    public Map add(@RequestBody Map<String, Object> request)
    {
        Map<String, Object> map = new HashMap<>();
        try{

            String userName = (String) request.get("userName");
            String realName = (String) request.get("realName");

            List<Integer> genderList = (List<Integer>) request.get("gender");
            int gender = genderList.get(0);

            String password = (String) request.get("password");
            String phoneNumber = (String) request.get("phoneNumber");
            String email = (String) request.get("email");

            List<Integer> companyList = (List<Integer>) request.get("companyId");
            List<Integer> departmentList = (List<Integer>) request.get("departmentId");
            List<String> careerList = (List<String>) request.get("career");
            int companyId = companyList.get(0);
            int departmentId = departmentList.get(0);
            String career = careerList.get(0);

            String role = (String) request.get("role");

            int enabled = Integer.parseInt((String) request.get("enabled"));
            if(enabled == 2) enabled = 0;

            String description = (String) request.get("description");

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String createTime = now.format(formatter);

            User user = new User(companyId,userName,realName, gender,password, role, enabled, career, phoneNumber,email, createTime, description, departmentId);
            userBiz.addUserByPage(user);

            map.put("isOk",true);
            map.put("msg", "添加成功");
        }catch (Exception e) {
            map.put("isOk", false);
            map.put("msg", "注册失败：" + e.getMessage());
        }
        return map;
    }

    @RequestMapping("/update")
    public Map update(@RequestBody Map<String, Object> request)
    {
        System.out.println("123456");
        Map<String, Object> map = new HashMap<>();
        try{
            int userId = (int)request.get("userId");
            String userName = (String) request.get("userName");
            String realName = (String) request.get("realName");
            int companyId = (int) request.get("companyId");
            int gender = (int) request.get("gender");
            System.out.println(companyId);
            System.out.println(gender);

            String career = (String) request.get("career");
            String password = (String) request.get("password");
            String phoneNumber = (String) request.get("phoneNumber");
            String email = (String) request.get("email");

            List<Integer> departmentList = (List<Integer>) request.get("departmentId");
            int departmentId = departmentList.get(0);

            String role = (String) request.get("role");

            int enabled = Integer.parseInt((String) request.get("enabled"));
            if(enabled == 2) enabled = 0;

            String description = (String) request.get("description");

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String createTime = now.format(formatter);

            User user = new User(userId, companyId,userName,realName, gender,password, role, enabled, career, phoneNumber,email, createTime, description, departmentId);
            userBiz.updateUserByPage(user);
            map.put("isOk",true);
            map.put("msg", "修改成功");
        }catch (Exception e) {
            map.put("isOk", false);
            map.put("msg", "修改失败：" + e.getMessage());
        }
        return map;
    }

    @RequestMapping("/deleteUserById")
    public Map delete(Integer userId){
        boolean isOk = userBiz.deleteUserById(userId);
        Map map = new HashMap();
        if(isOk){
            map.put("isOk",true);
            map.put("msg","删除成功");
        }else{
            map.put("isOk",false);
            map.put("msg","删除失败");
        }
        return map;
    }


}
