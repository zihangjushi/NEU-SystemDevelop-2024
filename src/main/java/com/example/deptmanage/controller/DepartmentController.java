package com.example.deptmanage.controller;

import com.example.deptmanage.biz.*;
import com.example.deptmanage.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dept")
@CrossOrigin(originPatterns = "http://localhost:8081")

public class DepartmentController {
    @Autowired
    private DepartmentBiz departmentBiz;
    private CompanyBiz companyBiz;
    @RequestMapping("/deptlist")
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
        if(deptlist.size() == 0){
            map.put("isOk", false);
        }
        else{
            map.put("isOk",true);
            map.put("deptlistByCompany",deptlist);
            map.put("total", deptlist.size());
            map.put("msg","查询成功");
        }
        return map;
    }

    @RequestMapping("/list")
    public Map list(){
        List<Department> deptlist = departmentBiz.getDepartmentList();
        List<Company> companyList = companyBiz.getCompanyList();
        Map map = new HashMap();
        map.put("isOk",true);
        map.put("departments",deptlist);
        map.put("companies", companyList);
        map.put("total", deptlist.size() + companyList.size());
        map.put("msg","查询成功");
        return map;
    }


}
