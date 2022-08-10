package com.leilei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication

public class AppLocationCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppLocationCenterApplication.class, args);
    }

}
