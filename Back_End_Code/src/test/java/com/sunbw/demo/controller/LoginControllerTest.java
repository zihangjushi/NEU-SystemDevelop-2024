package com.sunbw.demo.controller;

import com.sunbw.demo.biz.UserBiz;
import com.sunbw.demo.controller.LoginController;
import com.sunbw.demo.entity.User;
import com.sunbw.demo.request.LoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LoginControllerTest {

    @Mock
    private UserBiz userBiz;

    @InjectMocks
    private LoginController loginController;

    @Mock
    private HttpSession httpSession;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin() {
        // Mock data
        LoginRequest request = new LoginRequest();
        request.setUsernum("testUser");
        request.setPassword("testPassword");

        // Mock user data
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setUserid(1); // 设置用户ID
        user.setUsernum("testUser");
        user.setPassword("testPassword");
        user.setRole("admin");
        user.setUsername("Test User"); // 设置用户名
        userList.add(user);

        // Mock userBiz.getUserList() 返回模拟的用户列表
        when(userBiz.getUserList()).thenReturn(userList);

        // Mock HttpSession 的属性设置
        HttpSession httpSession = mock(HttpSession.class);
        when(httpSession.getAttribute("name")).thenReturn("Test User");
        when(httpSession.getAttribute("role")).thenReturn("admin");
        when(httpSession.getAttribute("userid")).thenReturn(1); // 设置用户ID的模拟返回

        // 预期的登录结果
        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("isOk", true);
        expectedResult.put("user", "testUser");
        expectedResult.put("role", "admin");
        expectedResult.put("msg", "登录成功");

        // 测试登录方法
        Map<String, Object> result = loginController.login(request, httpSession);

        // 验证结果是否符合预期
        assertEquals(expectedResult, result);

        // 验证 HttpSession 中属性的设置
        verify(httpSession, times(1)).setAttribute(eq("userid"), eq(1)); // 验证设置用户ID
        verify(httpSession, times(1)).setAttribute(eq("usernum"), eq("testUser"));
        verify(httpSession, times(1)).setAttribute(eq("name"), eq("Test User"));
        verify(httpSession, times(1)).setAttribute(eq("role"), eq("admin"));
    }

    @Test
    public void testGetCompanies() {
        // Mock data
        List<String> companyList = new ArrayList<>();
        companyList.add("Company A");
        companyList.add("Company B");

        when(userBiz.getCompanynameList()).thenReturn(companyList);

        Map<String, Object> expectedResult = new HashMap<>();
        expectedResult.put("isOk", true);
        expectedResult.put("companies", companyList);

        // Test
        Map<String, Object> result = loginController.getCompanies(httpSession);

        assertEquals(expectedResult, result);
    }

    // Similarly, you can write tests for other methods like register, getUserInfo, and logout.
}
