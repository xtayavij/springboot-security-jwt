package com.jun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.jun"})
public class SpringbootSecurityJWTApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSecurityJWTApplication.class, args);
    }

}
