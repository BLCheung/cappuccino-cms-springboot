package com.blcheung.zblmissyouadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = { "com.blcheung.zblmissyouadmin.mapper" })
public class ZblMissyouAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZblMissyouAdminApplication.class, args);
    }

}
