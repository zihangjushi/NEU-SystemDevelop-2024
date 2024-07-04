package com.NEUSystemDevelop2024.biz;

import com.NEUSystemDevelop2024.entity.Department;
import com.NEUSystemDevelop2024.mapper.CompanyMapper;
import com.NEUSystemDevelop2024.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentBiz {
    @Autowired
    private DepartmentMapper mapper;

    @Autowired
    private CompanyMapper companyMapper;

    public List<Department> getDepartmentList()
    {
        return mapper.listDepartment();
    }

    public void addDepartment(Department department) {
        int companyId = department.getCompanyId();
        List<Department> departmentList = companyMapper.searchDeptByCompanyId(companyId);
        department.setSerialId(departmentList.size() + 1);
        mapper.insertDept(department);
    }

    public boolean deleteDepartment(Integer departmentId) {
        return mapper.deleteDept(departmentId);
    }

    public boolean updateDepartment(Department department) {
        return mapper.updateDept(department);
    }

    public List<Department> searchByCompanyId(int comId) {
        return mapper.searchByCompanyId(comId);
    }


}
