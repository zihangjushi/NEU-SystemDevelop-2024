package com.NEUSystemDevelop2024.mapper;

import com.NEUSystemDevelop2024.entity.Department;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    @Insert("insert into t_department(departmentId, serialId, companyId, departmentName, adminName, phoneNumber, state, description) " +
            "values (#{departmentId}, #{serialId}, #{companyId}, #{departmentName}, #{adminName}, #{phoneNumber}, #{state}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "departmentId", keyColumn = "departmentId")
    void insertDept(Department department);


    @Delete("delete from t_department where departmentId=#{departmentId}")
    boolean deleteDept(int deptId);

    @Update("update t_department set   departmentName=#{department.departmentName}, adminName=#{department.adminName}, " +
            "phoneNumber=#{department.phoneNumber}, state=#{department.state}, description=#{department.description} where departmentId=#{department.departmentId}")
    boolean updateDept(@Param("department") Department department);

    @Select("select * from t_department")
    List<Department> listDepartment();

    @Select("select * from t_department where companyId=#{companyId}")
        // 应该是对的
    List<Department> searchByCompanyId(Integer companyId);

    @Select("select * from t_department where   state=#{inputDeptstate} ")
    List<Department> searchByState(@Param("inputDeptstate") Integer inputDeptstate);

    @Select("select * from t_department where departmentName LIKE CONCAT('%', #{inputDeptname}, '%') ")
    List<Department> searchByDepName(@Param("inputDeptname") String inputDeptname);

    @Select("select * from t_department where departmentName LIKE CONCAT('%', #{inputDeptname}, '%') and state=#{inputDeptstate} ")
    List<Department> search(@Param("inputDeptname") String inputDeptname, @Param("inputDeptstate") Integer inputDeptstate);


}
