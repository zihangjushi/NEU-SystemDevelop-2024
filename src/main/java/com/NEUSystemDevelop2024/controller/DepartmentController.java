package com.NEUSystemDevelop2024.controller;

import com.NEUSystemDevelop2024.biz.DepartmentBiz;
import com.NEUSystemDevelop2024.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentBiz departmentBiz;

    @RequestMapping("/list")
    public Map listDepartment(){
        List<Department> list = departmentBiz.getDepartmentList();
        Map map = new HashMap();
        map.put("isOk",true);
        map.put("students",list);
        map.put("msg","查询成功");
        return map;
    }
}
