package com.NEUSystemDevelop2024.controller;

import com.NEUSystemDevelop2024.entity.Company;
import com.NEUSystemDevelop2024.biz.CompanyBiz;
import com.NEUSystemDevelop2024.entity.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
        Map map = new HashMap();
        map.put("isOk",true);
        map.put("company",list);
        map.put("msg","查询成功");
        return map;
    }

    @RequestMapping("/getcompanynames")
    public Map<String, Object> getCompanyNames() {
        List<String> companies = companyBiz.getCompanynameList();

        Map<String, Object> map = new HashMap<>();
        map.put("isOk", true);
        map.put("companies", companies);

        return map;
    }

    @RequestMapping("/deleteCompanyById")
    public Map delete(Integer companyId){
        System.out.println(companyId);
        boolean isOk = companyBiz.deleteCompanyById(companyId);
        Map map = new HashMap();
        if(isOk){
            map.put("isOk",true);
            map.put("msg","删除成功");
        }else{
            map.put("isOk",false);
            map.put("msg","删除失败");
        }
        return map;
    }

    @RequestMapping("/edit")
    public Map editNews(@RequestBody Map request) {

        int companyId = (int)request.get("companyId");
        int state = (int) request.get("state");
        String companyName = (String) request.get("companyName");
        String contactName = (String) request.get("contactName");
        String phoneNumber = (String) request.get("phoneNumber");
        String adminName = (String) request.get("adminName");
        String content = (String) request.get("content");
        String imageUrl = (String) request.get("imageUrl");

        Company company = new Company(companyId, adminName, state, phoneNumber, contactName,companyName, content, null, imageUrl);
        Map res = new HashMap();
        try {
            boolean result = companyBiz.updateCompany(company);
            if (result) {
                res.put("isOk", true);
            } else {
                res.put("isOk", false);
            }
        } catch (Exception e) {
            res.put("isOk",false);
        }
        return res;
    }
}
