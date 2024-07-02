package com.example.deptmanage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude= {SecurityAutoConfiguration.class })
public class DeptManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeptManageApplication.class, args);
    }

}
