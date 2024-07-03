package com.example.deptmanage.controller;

import com.example.deptmanage.biz.DepartmentBiz;
import com.example.deptmanage.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/department")
//@CrossOrigin(originPatterns = "http://localhost:8081")

public class DepartmentController {
    @Autowired
    private DepartmentBiz departmentBiz;
    @Autowired
    private DepartmentBiz DepartmentBiz;


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
