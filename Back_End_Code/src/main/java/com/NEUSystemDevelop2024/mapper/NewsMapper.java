package com.NEUSystemDevelop2024.mapper;

import com.NEUSystemDevelop2024.entity.News;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface NewsMapper {

    @Insert("INSERT INTO t_news (title, image, content, author, introduction, companyName, createTime) " +
            "VALUES (#{title}, #{image}, #{content}, #{author}, #{introduction}, #{companyName}, #{createTime})")
    int insertNews(News news);

    @Update("UPDATE t_news SET title = #{title}, image = #{image}, content = #{content}, author = #{author}, " +
            "introduction = #{introduction}, companyName = #{companyName} " +
            "WHERE newsId = #{newsId}")
    int updateNews(News news);


    @Select("SELECT * FROM t_news")
    List<News> listNews();

    @Select("SELECT COUNT(*) FROM t_news")
    int countNews();

    @Select("SELECT * FROM t_news LIMIT #{startIndex}, #{pageSize}")
    List<News> findNewsByPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    @Delete("<script>" +
            "DELETE FROM t_news WHERE newsId IN " +
            "<foreach item='id' collection='ids' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int deleteNewsByIds(@Param("ids") List<Integer> ids);

    @Select("SELECT * FROM t_news WHERE newsId=#{id}")
    News findNewsById(@Param("id") int id);

    @Select("SELECT * FROM t_news WHERE companyName=#{companyName}")
    List<News> listNewsByCompanyName(@Param("companyName") String companyName);

    @Select("SELECT companyName FROM t_company WHERE companyId = #{companyId}")
    String selectCompanynameByid(int companyId);
}
