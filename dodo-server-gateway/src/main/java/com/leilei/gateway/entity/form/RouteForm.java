package com.leilei.gateway.entity.form;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lei
 * @create 2022-04-11 15:24
 * @desc 路由模型
 **/
@Data
public class RouteForm {

    private String id;

    private String name;

    private List<PredicateInfo> predicates = new ArrayList<>();


    private List<FilterInfo> filters = new ArrayList<>();

    private String uri;

    private String description;

    private int order = 0;
}
