package com.NEUSystemDevelop2024.biz;

import com.NEUSystemDevelop2024.entity.Company;
import com.NEUSystemDevelop2024.mapper.CompanyMapper;
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
}
