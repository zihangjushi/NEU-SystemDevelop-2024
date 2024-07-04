package com.NEUSystemDevelop2024.mapper;

import com.NEUSystemDevelop2024.entity.Department;
import com.NEUSystemDevelop2024.entity.Company;
import org.apache.ibatis.annotations.*;
import com.NEUSystemDevelop2024.entity.News;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CompanyMapper {
    @Insert("INSERT INTO t_company (contactName, companyName, phoneNumber, adminName, state, description) " +
            "VALUES (#{contactName}, #{companyName}, #{phoneNumber}, #{adminName}, #{state}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "companyId", keyColumn = "companyId")
    void insertCompanyInDept(Company company);

    @Delete("delete from t_company where companyId=#{companyId}")
    boolean deleteCompany(Integer companyId);


    @Update("update t_company set contactName=#{company.contactName}, companyName=#{company.companyName}, phoneNumber=#{company.phoneNumber}, adminName=#{company.adminName}, state=#{company.state}, description=#{company.description} where companyId=#{company.companyId}")
    boolean updateCompanyInDept(@Param("company") Company company);


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

    @Select("select * from t_department where companyId=#{companyId}")
    List<Department> searchDeptByCompanyId(Integer companyId);



    @Select("select * from t_company  LIMIT #{pageNum}, #{pageSize}")
    List<Company> listDepartmentPage( @Param("pageNum") Long pageNum, @Param("pageSize") Long pageSize);





}
