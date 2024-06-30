package com.NEUSystemDevelop2024.mapper;

import com.NEUSystemDevelop2024.entity.Meeting;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface MeetingMapper {

    @Select("SELECT meetingId, meetingTitle, meetingDescription, startTime, endTime, organizer, state FROM meeting")
    List<Meeting> queryAll();

    @Select("SELECT meetingId, meetingTitle, meetingDescription, startTime, endTime, organizer, state FROM meeting WHERE meetingId = #{meetingId}")
    Meeting queryById(@Param("meetingId") int meetingId);

    @Insert("INSERT INTO meeting (meetingTitle, meetingDescription, startTime, endTime, organizer, state) " +
            "VALUES (#{meetingTitle}, #{meetingDescription}, #{startTime}, #{endTime}, #{organizer}, #{state})")
    @Options(useGeneratedKeys = true, keyProperty = "meetingId")
    int meetingAdd(Meeting meeting);

    @Update("<script>" +
            "UPDATE meeting " +
            "<set>" +
            "<if test='meetingTitle != null'>meetingTitle = #{meetingTitle},</if> " +
            "<if test='meetingDescription != null'>meetingDescription = #{meetingDescription},</if> " +
            "<if test='startTime != null'>startTime = #{startTime},</if> " +
            "<if test='endTime != null'>endTime = #{endTime},</if> " +
            "<if test='organizer != null'>organizer = #{organizer},</if> " +
            "<if test='state != null'>state = #{state}</if> " +
            "</set> " +
            "WHERE meetingId = #{meetingId}" +
            "</script>")
    int update(Meeting meeting);

    @Delete("DELETE FROM meeting WHERE meetingId = #{meetingId}")
    int delete(@Param("meetingId") int meetingId);
}