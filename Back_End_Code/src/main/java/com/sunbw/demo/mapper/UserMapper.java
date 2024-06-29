package com.sunbw.demo.mapper;

import com.sunbw.demo.entity.Company;
import com.sunbw.demo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from t_users")
    List<User> selectUsers();

    @Select("select * from t_users where usernum=#{usernum}")
    User selectUsersByNum(String usernum);

    @Insert("INSERT INTO t_users (companyid, username, usernum, password, role, createdate) VALUES (#{companyid}, #{username}, #{usernum}, #{password}, #{role}, #{createdate})")
    int insertUser(User user);

    @Insert("INSERT INTO t_company (companyname, contactname, contactphone) VALUES (#{companyname}, #{contactname}, #{contactphone})")
    int insertCompany(Company company);


    @Select("SELECT COUNT(*) FROM t_users WHERE usernum = #{usernum}")
    int checkUsernumExists(String usernum);

    @Select("SELECT companyName FROM t_company WHERE companyId = #{companyId}")
    String selectCompanynameByid(int companyId);

    @Select("SELECT companyName FROM t_company")
    List<String> selectAllCompanyname();

    @Select("SELECT companyId FROM t_company WHERE companyName = #{companyName}")
    int selectCompanyidByName(String companyName);

    @Select("SELECT COUNT(*) FROM t_company WHERE companyName = #{companyName}")
    int checkCompanyExists(String companyName);
}
