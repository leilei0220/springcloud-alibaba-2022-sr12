package com.leilei.gateway.service;

import javassist.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author lei
 * @create 2022-04-11 16:20
 * @desc 自定义内存路由管理仓，开启日志打印
 **/
@Service
@Log4j2
public class MyRouteDefinitionRepository implements RouteDefinitionRepository {

    public  final Map<String, RouteDefinition> routes = Collections.synchronizedMap(new LinkedHashMap<>());

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        Collection<RouteDefinition> values = routes.values();
        return Flux.fromIterable(values);
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap( r -> {
            log.info("新增路由信息:{}",r);
            routes.put(r.getId(), r);
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            log.info("删除路由信息,路由ID为:{}",id);
            if (routes.containsKey(id)) {
                routes.remove(id);
                return Mono.empty();
            }
            return Mono.defer(() -> Mono.error(new NotFoundException("RouteDefinition not found: "+routeId)));
        });
    }


}
