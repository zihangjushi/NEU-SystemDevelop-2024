package com.example.deptmanage.biz;

import com.example.deptmanage.entity.Company;
import com.example.deptmanage.entity.Department;
import com.example.deptmanage.entity.dto.QueryRequest;
import com.example.deptmanage.entity.vo.DataVo;
import com.example.deptmanage.mapper.CompanyMapper;
import com.example.deptmanage.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyBiz {
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private DepartmentMapper departmentMapper;

    public List<DataVo> getCompanyList(QueryRequest queryRequest) {
        String inputDeptname = queryRequest.getInputDeptname();
        Integer inputDeptstate = queryRequest.getInputDeptstate();
        Long pageNum = queryRequest.getPageNum();
        Long pageSize = queryRequest.getPageSize();
        pageNum = ((pageNum - 1) * pageSize);

        List<Company> companyList = companyMapper.listDepartmentPage(pageNum, pageSize);
        List<Department> departmentList;
        if ((inputDeptname != null && !inputDeptname.isEmpty()) && (inputDeptstate != null && !ObjectUtils.isEmpty(inputDeptstate))) {
            departmentList = departmentMapper.search(inputDeptname, inputDeptstate);
        } else if (inputDeptname != null && !inputDeptname.isEmpty()) {
            departmentList = departmentMapper.searchByDepName(inputDeptname);
        } else if (inputDeptstate != null && !ObjectUtils.isEmpty(inputDeptstate)) {
            departmentList = departmentMapper.searchByState(inputDeptstate);
        } else {
            departmentList = departmentMapper.listDepartment();
        }
        ArrayList<DataVo> dataVos = new ArrayList<>();
        for (Company company : companyList) {
            DataVo dataVo = new DataVo();
            dataVo.setId(company.getCompanyId());
            dataVo.setName(company.getCompanyName());
            dataVo.setSort(company.getCompanyId());
            dataVo.setStatus(company.getState());
            dataVo.setCreateTime(company.getCreateTime());
            dataVo.setContactName(company.getContactName());
            dataVo.setPhoneNumber(company.getPhoneNumber());
            dataVo.setAdminName(company.getAdminName());
            ArrayList<DataVo> depDataLlist = new ArrayList<>();
            for (Department department : departmentList) {
                DataVo depData = new DataVo();
                depData.setId(department.getDepartmentId());
                depData.setName(department.getDepartmentName());
                depData.setSort(department.getSerialId());
                depData.setStatus(department.getState());
                depData.setCreateTime(department.getCreateTime());
                depData.setPhoneNumber(department.getPhoneNumber());
                depData.setAdminName(department.getAdminName());
                if (department.getCompanyId() == (company.getCompanyId())) {
                    depDataLlist.add(depData);
                }
            }
            dataVo.setChildren(depDataLlist);
            dataVos.add(dataVo);
        }
        return dataVos;
    }

    public List<DataVo> searchByCompanyId(Integer companyId) {
        List<Department> departmentList = companyMapper.searchByCompanyId(companyId);
        ArrayList<DataVo> dataVos = new ArrayList<>(departmentList.size());
        for (Department department : departmentList) {
            DataVo dataVo = new DataVo();
            dataVo.setId(department.getDepartmentId());
            dataVo.setName(department.getDepartmentName());
            dataVo.setSort(department.getSerialId());
            dataVo.setStatus(department.getState());
            dataVo.setCreateTime(department.getCreateTime());
            dataVo.setPhoneNumber(department.getPhoneNumber());
            dataVo.setAdminName(department.getAdminName());
            dataVos.add(dataVo);
        }
        return dataVos;
    }


    public void addCompany(Company company) {
        companyMapper.insertCompany(company);
    }

    public boolean deleteCompany(Integer companyId) {
        return companyMapper.deleteCompany(companyId);
    }

    public boolean updateCompany(Company company) {
        return companyMapper.updateCompany(company);
    }

    public int getCompanyListTotal() {
        return companyMapper.listCompany().size();
    }
}
