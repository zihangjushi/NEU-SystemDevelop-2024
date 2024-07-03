package com.NEUSystemDevelop2024.service.Impl;

import com.NEUSystemDevelop2024.entity.Meeting;
import com.NEUSystemDevelop2024.mapper.MeetingMapper;
import com.NEUSystemDevelop2024.service.MeetingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService {

    @Resource
    private MeetingMapper meetingMapper;

    @Override
    public List<Meeting> findAllMeetings() {
        return meetingMapper.queryAll();
    }

    @Override
    public Meeting findMeetingById(int meetingId) {
        return meetingMapper.queryById(meetingId);
    }

    @Override
    public void addMeeting(Meeting meeting) {
        meetingMapper.meetingAdd(meeting);
    }

    @Override
    public void updateMeeting(Meeting meeting) {
        meetingMapper.update(meeting);
    }

    @Override
    public void deleteMeeting(int meetingId) {
        meetingMapper.delete(meetingId);
    }
}
