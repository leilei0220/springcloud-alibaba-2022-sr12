package com.leilei.gateway.entity.form;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author lei
 * @create 2022-04-11 15:23
 * @desc 断言模型
 **/
@Data
public class PredicateInfo {

    private String name;

    private Map<String, String> args = new LinkedHashMap<>();
}
