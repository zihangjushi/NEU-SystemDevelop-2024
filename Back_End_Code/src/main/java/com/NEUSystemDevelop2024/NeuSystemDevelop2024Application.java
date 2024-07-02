package com.NEUSystemDevelop2024;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableOpenApi
@MapperScan(basePackages = "com.NEUSystemDevelop2024.mapper")
public class NeuSystemDevelop2024Application {
    public static void main(String[] args) {
        SpringApplication.run(NeuSystemDevelop2024Application.class, args);
    }
}
