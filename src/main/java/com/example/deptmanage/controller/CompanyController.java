package com.example.deptmanage.controller;

import com.example.deptmanage.biz.CompanyBiz;
import com.example.deptmanage.entity.Company;
import com.example.deptmanage.entity.dto.QueryRequest;
import com.example.deptmanage.entity.vo.DataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyBiz companyBiz;


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

    @GetMapping("/list")
    public Map listCompany(QueryRequest queryRequest) {
        List<DataVo> dataVoList = companyBiz.getCompanyList(queryRequest);
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
        List<DataVo> dataVoList = companyBiz.searchByCompanyId(companyId);
        boolean isEmpty = dataVoList.isEmpty();
        String msg = isEmpty ? "查询失败" : "查询成功";
        Map map = new HashMap();
        map.put("isOk", !isEmpty);
        map.put("data", dataVoList);
        map.put("msg", msg);
        return map;
    }
}
