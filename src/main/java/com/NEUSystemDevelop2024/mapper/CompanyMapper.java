package com.NEUSystemDevelop2024.mapper;

import com.NEUSystemDevelop2024.entity.Company;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CompanyMapper {
    @Select("select * from t_company")
    List<Company> listCompany();
}
