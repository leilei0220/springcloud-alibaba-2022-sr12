package com.leilei.feign;

import com.leilei.entity.Location;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author lei
 * @create 2022-04-07 16:12
 * @desc 定位调用服务
 **/
@FeignClient(value = "app-location-center",fallback = LocationCenterFallback.class)
public interface LocationCenterFeign {

    @GetMapping("/location/last/{vehicleId}")
    Location getLastLocation(@PathVariable("vehicleId") Integer vehicleId);
}
