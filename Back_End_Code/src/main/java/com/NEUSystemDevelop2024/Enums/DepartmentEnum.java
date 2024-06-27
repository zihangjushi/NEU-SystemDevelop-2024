package com.NEUSystemDevelop2024.Enums;

public enum DepartmentEnum {
    TESTING("测试部门"),
    OPERATIONS("运维部门"),
    FINANCE("财务部门"),
    MARKETING("市场部门"),
    DEVELOPMENT("研发部门");

    private String description;

    DepartmentEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    // 添加静态方法检查描述
    public static boolean isValidDescription(String description) {

        for (DepartmentEnum department : DepartmentEnum.values()) {
            if (department.getDescription().equals(description)) {
                return true;
            }
        }
        return false;
    }
}
