package com.example.deptmanage.mapper;


import com.example.deptmanage.entity.Company;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CompanyMapper {
    @Select("select * from t_company")
    public List<Company> listCompany();

    @Select("select * from t_company where companyId=#{companyId}")
    public Company searchByCompanyId(Integer companyId);

    @Select("select * from t_company where companyName=#{companyName}")
    public Company searchByCompanyName(String companyName);


    @Insert("INSERT INTO t_company (contactName, companyName, phoneNumber, adminName, state, createTime, description) " +
            "VALUES (#{contactName}, #{companyName}, #{phoneNumber}, #{adminName}, #{state}, #{createTime}, #{description})")
    public void insertCompany(Company company);
}
