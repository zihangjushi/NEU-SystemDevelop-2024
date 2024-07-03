package com.NEUSystemDevelop2024.mapper;

import com.NEUSystemDevelop2024.entity.Course;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseMapper {
    @Select("select * from t_course")
    List<Course> listCourse();

    @Insert("insert into t_course values(DEFAULT, #{courseName}, #{companyName}, #{description}, #{courseOrder}, #{author}, DEFAULT, DEFAULT, #{imageUrl}, #{videoUrl})")
    int insertCourse(Course course);

    @Select("select * from t_course where (courseName LIKE CONCAT('%', #{courseName}, '%') OR #{courseName} = '') AND (courseOrder LIKE CONCAT('%', #{courseOrder}, '%') OR #{courseOrder} = '') AND (author LIKE CONCAT('%', #{author}, '%') OR #{author} = '') AND (createTime BETWEEN #{createTime} AND #{modifyTime})")
    List<Course> listCourseBySearch(Course course);

    @Delete("delete from t_course where courseId = #{courseId}")
    int deleteCourseById(Integer courseId);
}
