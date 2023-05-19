package com.example.hotelmanagementbackgroud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@MapperScan(basePackages = "com.example.hotelmanagementbackgroud.dao")
@EnableTransactionManagement
public class HotelmanagementbackgroudApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelmanagementbackgroudApplication.class, args);
    }

}

