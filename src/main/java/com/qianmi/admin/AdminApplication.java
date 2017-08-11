package com.qianmi.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.UnknownHostException;

@SpringBootApplication(scanBasePackages = {"com.qianmi.admin"})
@MapperScan("com.qianmi.admin.dao.mapper")
@EnableTransactionManagement
public class AdminApplication {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(AdminApplication.class, args);
    }
}