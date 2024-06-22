package com.NEUSystemDevelop2024.biz;

import com.NEUSystemDevelop2024.entity.Department;
import com.NEUSystemDevelop2024.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentBiz {
    @Autowired
    private DepartmentMapper mapper;
    public List<Department> getDepartmentList()
    {
        return mapper.listDepartment();
    }

}
