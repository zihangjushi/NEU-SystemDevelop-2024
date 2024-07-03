package com.NEUSystemDevelop2024.service;

import com.NEUSystemDevelop2024.entity.Meeting;

import java.util.List;

public interface MeetingService {

    List<Meeting> findAllMeetings();

    Meeting findMeetingById(int meetingId);

    void addMeeting(Meeting meeting);

    void updateMeeting(Meeting meeting);

    void deleteMeeting(int meetingId);
}
