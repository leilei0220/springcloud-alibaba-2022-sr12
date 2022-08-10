package com.leilei.feign;

import com.leilei.entity.Location;
import org.springframework.stereotype.Component;

/**
 * @author lei
 * @create 2022-04-12 16:17
 * @desc 熔断配置
 **/
@Component
public class LocationCenterFallback implements LocationCenterFeign {

    @Override
    public Location getLastLocation(Integer vehicleId) {
        Location location = new Location();
        location.setPlate("获取车辆失败!");
        return location;
    }
}
