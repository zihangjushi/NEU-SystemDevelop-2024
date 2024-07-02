package com.NEUSystemDevelop2024.controller;

import com.NEUSystemDevelop2024.biz.CourseBiz;
import com.NEUSystemDevelop2024.entity.Course;
import com.NEUSystemDevelop2024.entity.User;
import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseBiz courseBiz;

    @RequestMapping("/list")
    public Map list(HttpSession session)
    {
        User user = (User) session.getAttribute("user");
//        List<Course> courseList = null;
//        if(user.getRole().equals("超级管理员")) {
//            courseList = courseBiz.getCourseList();
//        } else {
//            courseList = courseBiz.getCourseListByCompanyName(user.getCompanyName());
//        }
        List<Course> courseList = courseBiz.getCourseList();
        Map map = new HashMap();
        map.put("isOk", true);
        map.put("courses", courseList);
        map.put("msg","查询成功");
        return map;
    }

    @PostMapping("/add")
    public Map add(@RequestBody Course course, HttpSession session) {
        course.setCompanyName(getCourseCompanyName(session));
        boolean ret = courseBiz.insertCourse(course);
        Map map = new HashMap();
        if(ret) {
            map.put("isOk", true);
            map.put("msg","添加成功");
        } else {
            map.put("isOk", false);
            map.put("msg", "添加失败");
        }
        return map;
    }

    @PostMapping("/search")
    public Map search(@RequestBody Course course, HttpSession session) {
//        User user = (User) session.getAttribute("user");
        if(course.getCourseName() == null) {
            course.setCourseName("");
        }
        if(course.getCourseOrder() == null) {
            course.setCourseOrder("");
        }
        if(course.getAuthor() == null) {
            course.setAuthor("");
        }
        if(course.getCreateTime() == null) {
            course.setCreateTime(new Timestamp(0));
        }
        if(course.getModifyTime() == null) {
            course.setModifyTime(new Timestamp(Long.MAX_VALUE));
        }
        System.out.println(course);
        List<Course> courseList = courseBiz.getCourseListBySearch(course);
        Map map = new HashMap();
        if(courseList == null || courseList.size() == 0) {
            map.put("isOk", false);
            map.put("courses", "");
        } else {
            map.put("isOk", true);
            map.put("courses", courseList);
        }

        return map;
    }

    private String getCourseCompanyName(HttpSession session) {
//        User user = (User) session.getAttribute("user");
//        if(user.getRole().equals("超级管理员")) {
//            return "超级管理员所属";
//        } else {
//            return
//        }
        return "测试用例";
    }

//    @RequestMapping("/upload")
//    Map upload(MultipartFile file) {
//        Map map = new HashMap();
//        if (file == null ||  file.isEmpty()) {
//            map.put("isOk", false);
//            map.put("msg", "图片不能为空");
//            return map;
//        }
//
//        // file 重命名
//        String oldName = file.getOriginalFilename();
//        String ext = oldName.substring(oldName.lastIndexOf("."));
//        String uuid = UUID.randomUUID().toString().replace("-", "");
//        String newName = uuid + ext;
//
//        // 上传图片
//        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
//        String prepath = applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath() +
//                "\\src\\main\\resources\\static\\images\\";
//        String path = prepath + newName;
//
//        try {
//            file.transferTo(new File(path));
//        } catch (IOException e) {
//            e.printStackTrace();
//            map.put("isOk", false);
//            map.put("msg", e.getMessage());
//            return map;
//        }
//
//        map.put("isOk", true);
//        map.put("msg", path);
//        return map;
//    }
}
