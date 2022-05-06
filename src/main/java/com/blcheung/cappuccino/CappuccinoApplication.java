package com.blcheung.cappuccino;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = { "com.blcheung.cappuccino.mapper" })
public class CappuccinoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CappuccinoApplication.class, args);
    }

}
