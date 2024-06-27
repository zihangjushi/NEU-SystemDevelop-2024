package com.NEUSystemDevelop2024.mapper;

import com.NEUSystemDevelop2024.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    @Select("select * from t_department")
    List<Department> listDepartment();

}
