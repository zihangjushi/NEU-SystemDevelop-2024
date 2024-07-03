package com.NEUSystemDevelop2024.mapper;

import com.NEUSystemDevelop2024.entity.Company;
import com.NEUSystemDevelop2024.entity.News;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CompanyMapper {
    @Select("select * from t_company")
    List<Company> listCompany();

    @Select("select * from t_company where companyId=#{companyId}")
    Company searchByCompanyId(Integer companyId);

    @Select("select * from t_company where companyName=#{companyName}")
    Company searchByCompanyName(String companyName);


    @Insert("INSERT INTO t_company (contactName, companyName, phoneNumber, adminName, state, createTime, description) " +
            "VALUES (#{contactName}, #{companyName}, #{phoneNumber}, #{adminName}, #{state}, #{createTime}, #{description})")
    void insertCompany(Company company);


    @Select("SELECT companyName FROM t_company")
    List<String> selectAllCompanyname();

    @Delete("delete from t_company where companyId=#{companyId}")
    Integer deleteCompanyById(Integer companyId);

    @Update("UPDATE t_company SET companyName = #{companyName}, image = #{image}, contactName = #{contactName}, phoneNumber = #{phoneNumber}, " +
            "adminName = #{adminName}, description = #{description} " +
            "WHERE companyId = #{companyId}")
    int updateCompany(Company company);
}
