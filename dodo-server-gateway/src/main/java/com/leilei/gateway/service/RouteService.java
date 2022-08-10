package com.leilei.gateway.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leilei.gateway.entity.po.RouteInfo;
import com.leilei.gateway.mapper.RouteMapper;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author lei
 * @create 2022-04-11 18:13
 * @desc 路由信息持久化
 **/
@Service
public class RouteService extends ServiceImpl<RouteMapper, RouteInfo> {


    public List<RouteDefinition> getRouteList() {
        List<RouteInfo> list = list();
        return list.stream().map(x -> {
            RouteDefinition routeDefinition = new RouteDefinition();
            routeDefinition.setId(x.getRouteId());
            routeDefinition.setPredicates(JSON.parseArray(x.getPredicates(), PredicateDefinition.class));
            routeDefinition.setFilters(JSON.parseArray(x.getFilters(), FilterDefinition.class));
            try {
                routeDefinition.setUri(new URI(x.getUri()));
                routeDefinition.setOrder(x.getOrderNum());
                routeDefinition.setMetadata(new HashMap<>(2));
                return routeDefinition;
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return null;
            }

        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

}
