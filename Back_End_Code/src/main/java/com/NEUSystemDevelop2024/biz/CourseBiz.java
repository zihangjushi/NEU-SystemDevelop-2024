package com.NEUSystemDevelop2024.biz;


import com.NEUSystemDevelop2024.entity.Course;
import com.NEUSystemDevelop2024.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseBiz {
    @Autowired
    private CourseMapper mapper;

    public List<Course> getCourseList(){ return mapper.listCourse();}

    public boolean insertCourse(Course course) { return mapper.insertCourse(course) > 0; }

    public List<Course> getCourseListBySearch(Course course) { return mapper.listCourseBySearch(course); }
}

