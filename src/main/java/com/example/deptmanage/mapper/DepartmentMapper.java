package com.example.deptmanage.mapper;

import com.example.deptmanage.entity.Department;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    @Select("select * from t_department")
    public List<Department> listDepartment();

    @Insert("insert into t_department(departmentId, serialId, companyId, departmentName, adminName, phoneNumber, state, description, createTime) " +
            "values (#{departmentId}, #{serialId}, #{companyId}, #{departmentName}, #{adminName}, #{phoneNumber}, #{state}, #{description}, #{createTime})")
    public void insertDept(Department dept);

    @Update("update t_department set serialId=#{serialId}, companyId=#{companyId}, departmentName=#{departmentName}, adminName=#{adminName}, phoneNumber=#{phoneNumber}, state=#{state}, description=#{description}, createTime=#{createTime} where departmentId=#{id}")
    public void updateDept(Department dept);

    @Delete("delete from t_department where departmentId=#{departmentId}")
    public void deleteDept(int deptId);

    @Select("select * from t_department where companyId=#{companyId}")  //应该是对的
    public List<Department> searchByCompanyId(Integer companyId);

}
