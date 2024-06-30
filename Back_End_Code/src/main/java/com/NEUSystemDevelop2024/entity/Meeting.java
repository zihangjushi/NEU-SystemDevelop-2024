package com.NEUSystemDevelop2024.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meeting {

    private Integer meetingId;

    @NotBlank(message = "会议标题必须填写")
    private String meetingTitle;

    private String meetingDescription;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @NotBlank(message = "组织者不能为空")
    private String organizer;

    private String state;
}

