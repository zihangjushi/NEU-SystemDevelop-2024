package com.example.deptmanage.mapper;

import com.example.deptmanage.entity.Department;
import com.example.deptmanage.entity.Company;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CompanyMapper {
    @Insert("INSERT INTO t_company (contactName, companyName, phoneNumber, adminName, state, description) " +
            "VALUES (#{contactName}, #{companyName}, #{phoneNumber}, #{adminName}, #{state}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "companyId", keyColumn = "companyId")
    void insertCompany(Company company);

    @Delete("delete from t_company where companyId=#{companyId}")
    boolean deleteCompany(Integer companyId);


    @Update("update t_company set contactName=#{company.contactName}, companyName=#{company.companyName}, phoneNumber=#{company.phoneNumber}, adminName=#{company.adminName}, state=#{company.state}, description=#{company.description} where companyId=#{company.companyId}")
    boolean updateCompany(@Param("company") Company company);


    @Select("select * from t_company")
    List<Company> listCompany();

    @Select("select * from t_department where companyId=#{companyId}")
    List<Department> searchByCompanyId(Integer companyId);



    @Select("select * from t_company  LIMIT #{pageNum}, #{pageSize}")
    List<Company> listDepartmentPage( @Param("pageNum") Long pageNum, @Param("pageSize") Long pageSize);





}
