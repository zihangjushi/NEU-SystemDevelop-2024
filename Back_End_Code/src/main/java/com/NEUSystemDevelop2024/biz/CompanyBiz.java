package com.NEUSystemDevelop2024.biz;

import com.NEUSystemDevelop2024.entity.Company;
import com.NEUSystemDevelop2024.mapper.CompanyMapper;
import io.swagger.models.auth.In;
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

    public boolean deleteCompanyById(Integer companyId)
    {
        return companyMapper.deleteCompanyById(companyId) > 0;
    }
    public List<String> getCompanynameList(){
        return companyMapper.selectAllCompanyname();
    }

    public boolean updateCompany(Company company){
        return companyMapper.updateCompany(company) > 0;
    }
}
