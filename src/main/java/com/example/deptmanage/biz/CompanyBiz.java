package com.example.deptmanage.biz;

import com.example.deptmanage.entity.Company;
import com.example.deptmanage.mapper.CompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyBiz {
    @Autowired
    private CompanyMapper companyMapper;

    public List<Company> getCompanyList()
    {
        return companyMapper.listCompany();
    }

    public Company searchByCompanyId(Integer companyId){
        return companyMapper.searchByCompanyId(companyId);
    }

    public Company searchByCompanyName(String companyName){
        return companyMapper.searchByCompanyName(companyName);
    }

    public void addCompany(Company company) {
        companyMapper.insertCompany(company);
    }
}
