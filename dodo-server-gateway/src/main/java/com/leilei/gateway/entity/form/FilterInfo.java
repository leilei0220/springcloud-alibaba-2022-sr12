package com.leilei.gateway.entity.form;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author lei
 * @create 2022-04-11 15:22
 * @desc 过滤模型
 **/
@Data
public class FilterInfo {

    private String name;

    private Map<String, String> args = new LinkedHashMap<>();
}
