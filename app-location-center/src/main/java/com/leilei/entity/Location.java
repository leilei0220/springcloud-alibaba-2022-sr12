package com.leilei.entity;

import lombok.Data;

/**
 * @author lei
 * @create 2022-04-07 16:05
 * @desc 定位
 **/
@Data
public class Location {
    private Integer vehicleId;
    private String plate;
    private Integer latitude;
    private Integer longitude;
    private String clientInfo;
}
