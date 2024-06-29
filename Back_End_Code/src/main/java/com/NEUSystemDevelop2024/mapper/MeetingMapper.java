package com.NEUSystemDevelop2024.mapper;

import com.NEUSystemDevelop2024.entity.Meeting;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface MeetingMapper {

    List<Meeting> queryAll();

    Meeting queryById(int meetingId);

    int meetingAdd(Meeting meeting);

    int update(Meeting meeting);

    int delete(int meetingId);
}