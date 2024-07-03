package com.example.deptmanage.biz;

import com.example.deptmanage.entity.Department;
import com.example.deptmanage.mapper.CompanyMapper;
import com.example.deptmanage.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentBiz {
    @Autowired
    private DepartmentMapper mapper;

    @Autowired
    private CompanyMapper companyMapper;

    public void addDepartment(Department department) {
        int companyId = department.getCompanyId();
        List<Department> departmentList = companyMapper.searchByCompanyId(companyId);
        department.setSerialId(departmentList.size() + 1);
        mapper.insertDept(department);
    }

    public boolean deleteDepartment(Integer departmentId) {
        return mapper.deleteDept(departmentId);
    }

    public boolean updateDepartment(Department department) {
        return mapper.updateDept(department);
    }

    public List<Department> getDepartmentList() {
        return mapper.listDepartment();
    }


    public List<Department> searchByCompanyId(int comId) {
        return mapper.searchByCompanyId(comId);
    }


}
