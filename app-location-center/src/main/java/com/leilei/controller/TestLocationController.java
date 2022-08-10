package com.leilei.controller;

import com.leilei.entity.Location;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lei
 * @create 2022-04-07 16:04
 * @desc
 **/
@RestController
@RequestMapping("/location")
public class TestLocationController {
    @Value("${spring.cloud.nacos.discovery.ip}")
    private String ip;
    @Value("${server.port}")
    private Integer port;

    @GetMapping("/last/{vehicleId}")
    public Location getLocationByVehicleId(@PathVariable("vehicleId") Integer vehicleId) {
        Location location = new Location();
        location.setVehicleId(vehicleId);
        location.setPlate("川A0" + vehicleId);
        location.setLatitude(100023);
        location.setLongitude(2345);
        location.setClientInfo(ip + ":" + port);
        return location;
    }
}
