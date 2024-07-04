package com.NEUSystemDevelop2024.controller;

import com.NEUSystemDevelop2024.biz.CompanyBiz;
import com.NEUSystemDevelop2024.entity.Company;
import com.NEUSystemDevelop2024.entity.dto.QueryRequest;
import com.NEUSystemDevelop2024.entity.vo.DataVo;
import com.NEUSystemDevelop2024.entity.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;
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
    private CompanyBiz companyBiz;


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
    @PostMapping("/add")
    public Map addCompany(@RequestBody Company company) {
        companyBiz.addCompany(company);
        int companyId = company.getCompanyId();
        boolean flag = companyId > 0;
        String msg = flag ? "添加成功" : "添加失败";
        Map map = new HashMap();
        map.put("isOk", flag);
        map.put("data", companyId);
        map.put("msg", msg);
        return map;
    }

    @DeleteMapping("/delete/{companyId}")
    public Map deleteCompany(@PathVariable("companyId") Integer companyId) {
        boolean flag = companyBiz.deleteCompany(companyId);
        String msg = flag ? "删除成功" : "删除失败";
        Map map = new HashMap();
        map.put("isOk", flag);
        map.put("data", null);
        map.put("msg", msg);
        return map;
    }


    @PutMapping("/update")
    public Map updateCompany(@RequestBody Company company) {
        boolean flag = companyBiz.updateCompany(company);
        String msg = flag ? "修改成功" : "修改失败";
        Map map = new HashMap();
        map.put("isOk", flag);
        map.put("data", null);
        map.put("msg", msg);
        return map;
    }

    @GetMapping("/lists")
    public Map listCompanyInDept(QueryRequest queryRequest) {
        List<DataVo> dataVoList = companyBiz.getCompanyListInDept(queryRequest);
        int total= companyBiz.getCompanyListTotal();
        boolean isEmpty = dataVoList.isEmpty();
        String msg = isEmpty ? "查询失败" : "查询成功";
        Map map = new HashMap();
        map.put("isOk", !isEmpty);
        map.put("data", dataVoList);
        map.put("dataTotal", total);
        map.put("msg", msg);
        return map;
    }



    @GetMapping("/{companyId}")
    public Map getDepartmentByCompanyId(@PathVariable("companyId") Integer companyId) {
        System.out.println(companyId);
        List<DataVo> dataVoList = companyBiz.searchByCompanyIdInDept(companyId);
        boolean isEmpty = dataVoList.isEmpty();
        String msg = isEmpty ? "查询失败" : "查询成功";
        Map map = new HashMap();
        map.put("isOk", !isEmpty);
        map.put("data", dataVoList);
        map.put("msg", msg);
        return map;
    }

    
}
