package com.NEUSystemDevelop2024.controller;

import com.NEUSystemDevelop2024.entity.Company;
import com.NEUSystemDevelop2024.biz.CompanyBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    CompanyBiz companyBiz;

    @RequestMapping("/list")
    public Map listCompany(){
        List<Company> companyList = companyBiz.getCompanyList();
        Map map = new HashMap();
        map.put("isOk",true);
        map.put("companies",companyList);
        map.put("msg","查询成功");
        return map;
    }

    @RequestMapping("/searchByCompanyId")
    public Map searchCompanyByCompanyId(Integer companyId){
        Company company = companyBiz.searchByCompanyId(companyId);
        List<Company> list = new ArrayList<>();
        list.add(company);
        System.out.println(company.getCompanyName());
        Map map = new HashMap();
        map.put("isOk",true);
        map.put("company",list);
        map.put("msg","查询成功");
        return map;
    }
}
