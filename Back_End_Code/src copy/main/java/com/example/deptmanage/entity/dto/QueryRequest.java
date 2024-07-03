package com.example.deptmanage.entity.dto;

import lombok.Data;
@Data
public class QueryRequest {
    private String inputDeptname;

    private Integer inputDeptstate;

    private Long pageNum;

    private Long pageSize;

}
