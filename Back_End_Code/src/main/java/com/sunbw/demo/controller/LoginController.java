package com.sunbw.demo.controller;

import com.sunbw.demo.biz.UserBiz;
import com.sunbw.demo.entity.Company;
import com.sunbw.demo.entity.User;
import com.sunbw.demo.request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserBiz biz;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest request, HttpSession session) {
        List<User> userList = biz.getUserList();

        String userNum = request.getUsernum();
        String userPwd = request.getPassword();

        Map<String, Object> map = new HashMap<>();
        boolean loginSuccess = false;

        for (User user : userList) {
            if (user.getUsernum().equals(userNum) && user.getPassword().equals(userPwd)) {
                map.put("isOk", true);
                map.put("user", userNum);
                map.put("role", user.getRole());
                map.put("msg", "登录成功");
                session.setAttribute("userid", user.getUserid());
                session.setAttribute("usernum", userNum);
                session.setAttribute("name", user.getUsername());
                session.setAttribute("role", user.getRole());

                if (!"superadmin".equals(user.getRole())) {
                    if (user.getCompanyid() != null) {
                        session.setAttribute("companyname", biz.getCompanynameByid(user.getCompanyid()));
                    }
                }

                session.setAttribute("createdate", user.getCreatedate());
                loginSuccess = true;
                break;
            }
        }

        if (!loginSuccess) {
            map.put("isOk", false);
            map.put("msg", "您的账号或密码不正确，请重新输入！");
        }

        return map;
    }

    @GetMapping("/companies")
    public Map<String, Object> getCompanies(HttpSession session) {
        List<String> companies = biz.getCompanynameList();

        Map<String, Object> map = new HashMap<>();
        map.put("isOk", true);
        map.put("companies", companies);

        return map;
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, Object> request, HttpSession session) {
        Map<String, Object> map = new HashMap<>();

        try {
            String role = (String) request.get("role");
            String name = (String) request.get("username");
            String usernum = (String) request.get("usernum");
            String password = (String) request.get("password");

            if ("admin".equals(role)) {
                String newCompanyName = (String) request.get("newCompanyName");
                String contactName = (String) request.get("contactName");
                String contactPhone = (String) request.get("contactPhone");

                Company company = new Company(null, newCompanyName, contactName, contactPhone);
                biz.addCompany(company);

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = now.format(formatter);

                User user = new User(null, biz.getCompanyidByname(newCompanyName), name, usernum, password, formattedDateTime, role);
                biz.addUser(user);

            } else if ("employee".equals(role)) {
                String companyName = (String) request.get("companyName");

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = now.format(formatter);
                User user = new User(null, biz.getCompanyidByname(companyName), name, usernum, password, formattedDateTime, role);
                biz.addUser(user);
            }

            map.put("isOk", true);
            map.put("msg", "注册成功！");
        } catch (Exception e) {
            map.put("isOk", false);
            map.put("msg", "注册失败：" + e.getMessage());
        }

        return map;
    }

    @GetMapping("/info")
    public Map<String, Object> getUserInfo(HttpSession session) {
        Map<String, Object> userInfo = new HashMap<>();

        String name = (String) session.getAttribute("name");
        String role = (String) session.getAttribute("role");
        String companyname = (String) session.getAttribute("companyname");
        String createdate = (String) session.getAttribute("createdate");
        String usernum = (String) session.getAttribute("usernum");
        int userid = (int) session.getAttribute("userid");

        if (name == null || role == null) {
            userInfo.put("isLogin", false);
            return userInfo;
        }

        userInfo.put("usernum", usernum);
        userInfo.put("companyname", companyname);
        userInfo.put("createdate", createdate);
        userInfo.put("name", name);
        userInfo.put("role", role);
        userInfo.put("userid", userid);
        userInfo.put("isLogin", true);

        return userInfo;
    }

    @GetMapping("/exit")
    public Map<String, Object> logout(HttpSession session) {
        session.invalidate();

        Map<String, Object> result = new HashMap<>();
        result.put("isOk", true);
        result.put("msg", "已退出登录！");
        return result;
    }
}

