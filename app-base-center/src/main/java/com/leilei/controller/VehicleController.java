package com.leilei.controller;

import com.leilei.entity.Location;
import com.leilei.feign.LocationCenterFeign;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author lei
 * @create 2022-04-07 16:11
 * @desc
 **/
@RequestMapping("/vehicle")
@RestController
@RefreshScope
public class VehicleController {
    private final LocationCenterFeign locationFeign;

    @Value("${student.name:}")
    private String name;

    @Value("${adas.dbUrl}")
    private String dbUrl;

    @Value("${spring.data.mongodb.uri}")
    private String mongodbUrl;

    @Value("${extension.alarmType}")
    private String alarmType;
    public VehicleController(LocationCenterFeign locationFeign) {

        this.locationFeign = locationFeign;
    }


    @GetMapping("/last/location/{vehicleId}")
    public Location getVehicleLocationById(@PathVariable("vehicleId") Integer vehicleId) {
        System.out.println("读取自身配置" + name);
        System.out.println("读取共享配置-mysql" + dbUrl);
        System.out.println("读取共享配置-mongo" + mongodbUrl);
        System.out.println("读取扩展配置-alarmType" + alarmType);
        Location lastLocation = locationFeign.getLastLocation(vehicleId);
        if (lastLocation != null) {
            lastLocation.setRequestTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        return lastLocation;
    }

    @GetMapping("/test")
    public String getVehicleLocationById() {
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程:"+Thread.currentThread().getName()+"接口耗时：" + (System.currentTimeMillis() - start));
        return "aaaaa-test";
    }

}
