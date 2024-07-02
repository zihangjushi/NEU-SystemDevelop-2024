package com.NEUSystemDevelop2024.controller;

import com.NEUSystemDevelop2024.Enums.DepartmentEnum;
import com.NEUSystemDevelop2024.biz.CompanyBiz;
import com.NEUSystemDevelop2024.biz.DepartmentBiz;
import com.NEUSystemDevelop2024.entity.Company;
import com.NEUSystemDevelop2024.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class SearchController {
    @Autowired
    private CompanyBiz companyBiz;
    @Autowired
    private DepartmentBiz departmentBiz;

    @RequestMapping("/search")
    public Map searchMenuMap(String query)
    {
        Map map = new HashMap();
        List<Department> departmentList = departmentBiz.getDepartmentList();
        List<Company> companyList = companyBiz.getCompanyList();
        List<Department> departments = new ArrayList<>();
        List<Company> companies = new ArrayList<>();

        if("部门".contains(query))
        {
            companies = companyList;
            departments = departmentList;
        }
        else{
            for(Department department : departmentList)
            {
                if(department.getDepartmentName().contains(query)) departments.add(department);
            }
            for(Department department : departments)
            {
                companies.add(companyBiz.searchByCompanyId(department.getCompanyId()));
            }
        }
        map.put("isOk", true);
        map.put("companies", companies);
        map.put("departments",departments);
        map.put("msg","查询成功");
//        if(!DepartmentEnum.isValidDescription(query))
//        {
//            Company company = companyBiz.searchByCompanyName(query);
//            map.put("isOk",true);
//            map.put("companies", company);
//            map.put("departments", null);
//            map.put("msg", "公司及部门信息获取成功");
//        }
        return map;
    }
}