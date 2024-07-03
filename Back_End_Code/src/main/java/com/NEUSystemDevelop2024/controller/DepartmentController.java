package com.NEUSystemDevelop2024.controller;

import com.NEUSystemDevelop2024.biz.DepartmentBiz;
import com.NEUSystemDevelop2024.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    
    @Autowired
    private DepartmentBiz DepartmentBiz;

    @RequestMapping("/list")
    public Map listDepartment(){
        List<Department> list = DepartmentBiz.getDepartmentList();
        Map map = new HashMap();
        map.put("isOk",true);
        map.put("departments",list);
        map.put("msg","查询成功");
        return map;
    }


    @PostMapping("/add")
    public Map addDepartment(@RequestBody Department department) {
        DepartmentBiz.addDepartment(department);
        int departmentId = department.getDepartmentId();
        boolean flag = departmentId > 0;
        String msg = flag ? "添加成功" : "添加失败";
        Map map = new HashMap();
        map.put("isOk", flag);
        map.put("data", departmentId);
        map.put("msg", msg);
        return map;
    }

    @DeleteMapping("/delete/{departmentId}")
    public Map deleteDepartment(@PathVariable("departmentId") Integer departmentId) {
        boolean flag = DepartmentBiz.deleteDepartment(departmentId);
        String msg = flag ? "删除成功" : "删除失败";
        Map map = new HashMap();
        map.put("isOk", flag);
        map.put("data", null);
        map.put("msg", msg);
        return map;
    }


    @PutMapping("/update")
    public Map updateDepartment(@RequestBody Department department) {
        boolean flag = DepartmentBiz.updateDepartment(department);
        String msg = flag ? "修改成功" : "修改失败";
        Map map = new HashMap();
        map.put("isOk", flag);
        map.put("data", null);
        map.put("msg", msg);
        return map;
    }


}
