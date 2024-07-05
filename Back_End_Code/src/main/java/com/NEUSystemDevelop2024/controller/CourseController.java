package com.NEUSystemDevelop2024.controller;

import com.NEUSystemDevelop2024.biz.CompanyBiz;
import com.NEUSystemDevelop2024.biz.CourseBiz;
import com.NEUSystemDevelop2024.controller.explorer.ExcelExporter;
import com.NEUSystemDevelop2024.entity.Course;
import com.NEUSystemDevelop2024.entity.User;
import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseBiz courseBiz;

    @Autowired
    private CompanyBiz companyBiz;

    @Autowired
    private ExcelExporter excelExporter;

    @RequestMapping("/list")
    public Map list(HttpSession session)
    {
        List<Course> courseList = courseBiz.getCourseList();
        Map map = new HashMap();
        map.put("isOk", true);
        map.put("courses", courseList);
        map.put("msg","查询成功");
        return map;
    }

    @PostMapping("/add")
    public Map add(@RequestBody Course course, HttpSession session) {
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

    @PostMapping("/searchById")
    public Map searchById(@RequestBody Map<String, Integer> requestBody, HttpSession session) {
        Course course = courseBiz.getCourseById(requestBody.get("Id"));
        Map map = new HashMap();
        if(course != null) {
            map.put("isOk", true);
            map.put("course", course);
            map.put("mgs", "查找成功");
        }else{
            map.put("isOk", false);
            map.put("course", "");
            map.put("mgs", "查找失败");
        }
        return map;
    }

    @PostMapping("/edit")
    public Map edit(@RequestBody Course course, HttpSession session) {
        boolean ret = courseBiz.updateCourse(course);
        Map map = new HashMap();
        if(ret) {
            map.put("isOk", true);
            map.put("msg", "编辑成功");
        } else {
            map.put("isOk", false);
            map.put("msg", "编辑失败");
        }
        return map;
    }

    @PostMapping("/search")
    public Map search(@RequestBody Course course, HttpSession session) {
        if(course.getCourseName() == null || course.getCourseName().equals("null") || course.getCourseName().equals("")) {
            course.setCourseName("");
        }
        if(course.getCourseOrder() == null || course.getCourseOrder().equals("null") || course.getCourseOrder().equals("")) {
            course.setCourseOrder("");
        }
        if(course.getAuthor() == null || course.getAuthor().equals("null") || course.getAuthor().equals("")) {
            course.setAuthor("");
        }
        if(course.getCreateTime() == null || course.getCreateTime().equals("null") || course.getCreateTime().equals("")) {
            course.setCreateTime(new Timestamp(0));
        }
        if(course.getModifyTime() == null) {
            long val = System.currentTimeMillis();
            course.setModifyTime(new Timestamp(val));
        }
        List<Course> courseList = courseBiz.getCourseListBySearch(course);
        Map map = new HashMap();
        if(courseList == null || courseList.size() == 0) {
            map.put("isOk", false);
            map.put("courses", "");
            map.put("msg", "查找失败");
        } else {
            map.put("isOk", true);
            map.put("courses", courseList);
            map.put("msg", "查找成功");
        }

        return map;
    }

    @PostMapping("/deleteList")
    public Map deleteList(@RequestBody Map<String, List<Integer>> requestBody, HttpSession session) {
        List<Integer> idList = requestBody.get("ids");
        Map map = new HashMap();
        boolean ok = true;
        for(Integer id : idList) {
            boolean ret = courseBiz.deleteCourse(id);
            if(!ret) {
                ok = false;
            }
        }
        if(ok) {
            map.put("isOk", true);
            map.put("msg", "删除成功");
        } else {
            map.put("isOk", false);
            map.put("msg", "删除失败");
        }

        return map;
    }

    @PostMapping("/deleteOne")
    public Map deleteOne(@RequestBody Map<String, Integer> requestBody, HttpSession session) {
        Map map = new HashMap();
        Integer id = requestBody.get("courseId");
        boolean ok = courseBiz.deleteCourse(id);
        if(ok) {
            map.put("isOk", true);
            map.put("msg", "删除成功");
        } else {
            map.put("isOk", false);
            map.put("msg", "删除失败");
        }
        return map;
    }

    @PostMapping("/companyName")
    public Map getCompanyNameById(@RequestBody Map<String, Integer> request, HttpSession session) {
        Integer Id = request.get("Id");
        System.out.println(Id);
        String companyName = companyBiz.searchByCompanyId(Id).getCompanyName();
        Map map = new HashMap();
        map.put("isOk", true);
        map.put("companyName", companyName);
        return map;
    }

    @RequestMapping("/export")
    public Map export(HttpSession session) {
        List<Course> courseList = courseBiz.getCourseList();
        String base64String = exportCourses(courseList);
        Map map = new HashMap();
        map.put("isOk", true);
        map.put("courses", base64String);
        map.put("msg", "导出成功");
        return map;
    }


    public String exportCourses(List<Course> courseList) {
        ByteArrayOutputStream outputStream = excelExporter.exportToExcel(courseList);
        byte[] bytes = outputStream.toByteArray();

        // 将字节数组转换为Base64编码的字符串
        String base64EncodedString = Base64Utils.encodeToString(bytes);
        return base64EncodedString;
    }

}
