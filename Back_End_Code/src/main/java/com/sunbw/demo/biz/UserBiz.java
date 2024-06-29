package com.sunbw.demo.biz;

import com.sunbw.demo.entity.Company;
import com.sunbw.demo.entity.User;
import com.sunbw.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBiz {

    @Autowired
    private UserMapper mapper;



    public List<User> getUserList(){
        return mapper.selectUsers();
    }

    public int addUser(User user){ return mapper.insertUser(user);}


    public int checkUsernumExists(String usernum){
        return  mapper.checkUsernumExists(usernum);
    }

    public int checkCompanyExists(String companyname){
        return  mapper.checkUsernumExists(companyname);
    }

    public void setMapper(UserMapper mapper) {
        this.mapper = mapper;
    }


    public String getCompanynameByid(int id){
        return mapper.selectCompanynameByid(id);
    }

    public List<String> getCompanynameList(){
        return mapper.selectAllCompanyname();
    }

    public int getCompanyidByname(String name){
        return mapper.selectCompanyidByName(name);
    }

    public int addCompany(Company company){
        return mapper.insertCompany(company);
    }
}
