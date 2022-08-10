package com.leilei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.leilei.feign")
@EnableDiscoveryClient
public class AppBaseCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppBaseCenterApplication.class, args);
    }

}
