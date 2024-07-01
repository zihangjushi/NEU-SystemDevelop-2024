package com.example.deptmanage.biz;

import com.example.deptmanage.entity.Department;
import com.example.deptmanage.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentBiz {
    @Autowired
    private DepartmentMapper mapper;

    public List<Department> getDepartmentList() {
        return mapper.listDepartment();
    }

    public void insertDept(Department dept){
        mapper.insertDept(dept);
    }

    public void updateDept(Department dept){
        mapper.updateDept(dept);
    }

    public void deleteDept(int deptId){
        mapper.deleteDept(deptId);
    }

    public List<Department> searchByCompanyId(int comId){
        return mapper.searchByCompanyId(comId);
    }
}
