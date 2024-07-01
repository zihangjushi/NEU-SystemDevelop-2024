package com.example.deptmanage.controller;

import com.example.deptmanage.biz.DepartmentBiz;
import com.example.deptmanage.entity.Company;
import com.example.deptmanage.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dept")

public class DepartmentController {
    @Autowired
    private DepartmentBiz departmentBiz;
    @RequestMapping("/list")
    public Map listDepartment(){
        List<Department> list = departmentBiz.getDepartmentList();
        Map map = new HashMap();
        map.put("isOk",true);
        map.put("departments",list);
        map.put("msg","查询成功");
        return map;
    }

    @RequestMapping("/insertDept")
    public Map insertDept(Department dept){
        Map map = new HashMap();
        map.put("isOk",true);
        map.put("msg","查询成功");
        return map;
    }
    @RequestMapping("/searchByCompanyId")
    public Map searchByCompanyId(int id){
        System.out.println(id);
        List<Department> deptlist = departmentBiz.searchByCompanyId(id);

        Map map = new HashMap();
        map.put("isOk",true);
        map.put("deptlistByCompany",deptlist);
        map.put("msg","查询成功");
        return map;
    }



}
