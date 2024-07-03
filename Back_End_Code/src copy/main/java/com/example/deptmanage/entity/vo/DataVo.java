package com.example.deptmanage.entity.vo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
@Data
public class DataVo {
    private Integer id;

    private String name;

    private Integer sort;

    private Integer status;
    private String contactName;
    private String phoneNumber;
    private String adminName;

    private Timestamp createTime;

    private List<DataVo> children;
}
