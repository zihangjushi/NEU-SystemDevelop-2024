package com.NEUSystemDevelop2024.controller;

import com.NEUSystemDevelop2024.common.Result;
import com.NEUSystemDevelop2024.entity.Meeting;
import com.NEUSystemDevelop2024.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @GetMapping("/all")
    public Result<List<Meeting>> findAllMeetings() {
        return Result.success(meetingService.findAllMeetings());
    }


    @GetMapping("/{meetingId}")
    public Result<Meeting> findMeetingById(@PathVariable int meetingId) {
        return Result.success(meetingService.findMeetingById(meetingId));
    }

    @PostMapping("/add")
    public Result addMeeting(@RequestBody @Validated Meeting meeting) {
        meetingService.addMeeting(meeting);
        return Result.success();
    }

    @PutMapping("/update")
    public Result updateMeeting(@RequestBody Meeting meeting) {
        meetingService.updateMeeting(meeting);
        return Result.success();
    }

    @DeleteMapping("/{meetingId}")
    public Result deleteMeeting(@PathVariable int meetingId) {
        meetingService.deleteMeeting(meetingId);
        return Result.success();
    }
}
