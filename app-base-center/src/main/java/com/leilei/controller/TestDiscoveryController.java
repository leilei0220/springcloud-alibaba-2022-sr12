package com.leilei.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lei
 * @create 2022-04-07 16:30
 * @desc
 **/
@RestController
@RequestMapping("/nacos")
public class TestDiscoveryController {

    private final DiscoveryClient discoveryClient;

    public TestDiscoveryController(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Value("${spring.application.name}")
    private String currentServerName;

    @GetMapping("/discovery")
    public List<ServiceInstance> getServicesList() {
        List<ServiceInstance> serviceInstances = new ArrayList<>();
        //获取服务名称
        List<String> serviceNames = discoveryClient.getServices()
                .stream()
                .filter(e -> currentServerName.equals(e))
                .collect(Collectors.toList());

        for (String serviceName : serviceNames) {
            //获取服务中的实例列表
            serviceInstances = discoveryClient.getInstances(serviceName);
        }
        return serviceInstances;
    }

}
