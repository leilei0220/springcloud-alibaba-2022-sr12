package com.leilei.gateway.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leilei.gateway.entity.Result;
import com.leilei.gateway.entity.form.RouteForm;
import com.leilei.gateway.entity.po.RouteInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lei
 * @create 2022-04-11 15:35
 * @desc
 **/
@Service
@Log4j2
public class DynamicRouteService implements ApplicationEventPublisherAware, ApplicationRunner {

    private final RouteDefinitionWriter routeDefinitionWriter;

    private final RouteService routeService;

    private ApplicationEventPublisher publisher;

    public DynamicRouteService(RouteDefinitionWriter routeDefinitionWriter, RouteService routeService) {
        this.routeDefinitionWriter = routeDefinitionWriter;
        this.routeService = routeService;
    }


    /**
     * 增加路由
     *
     * @param routeForm
     * @return
     */
    public Result<Boolean> add(RouteForm routeForm) {
        RouteDefinition definition = convert(routeForm);
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        enduranceRule(routeForm.getName(), routeForm.getDescription(), definition);
        publishRouteEvent();
        System.out.println(JSON.toJSONString(definition));
        return Result.success(true);
    }

    /**
     * 更新路由
     *
     * @param routeForm
     * @return
     */
    public Result<Boolean> update(RouteForm routeForm) {
        RouteDefinition definition = convert(routeForm);
        try {
            this.routeDefinitionWriter.delete(Mono.just(definition.getId())).subscribe();
        } catch (Exception e) {
            return Result.fail("未知路由信息");
        }
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            enduranceRule(routeForm.getName(), routeForm.getDescription(), definition);
            publishRouteEvent();
            return Result.success(true);
        } catch (Exception e) {
            return Result.fail("路由信息修改失败!");
        }
    }

    /**
     * 删除路由
     *
     * @param id
     * @return
     */
    public Result<Boolean> delete(String id) {
        this.routeDefinitionWriter.delete(Mono.just(id)).subscribe();
        routeService.remove(buildWrapper(id));
        publishRouteEvent();
        return Result.success();
    }

    private void publishRouteEvent() {
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }


    public Result<Boolean> flushRoute() {
        publishRouteEvent();
        return Result.success(true);
    }

    public LambdaQueryWrapper<RouteInfo> buildWrapper(String routeId) {
        return new QueryWrapper<RouteInfo>().lambda()
                .eq(RouteInfo::getRouteId, routeId)
                .last("limit 1");
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }


    @Override
    public void run(ApplicationArguments args) {
        log.info("----------从持久层加载额外路由信息---------");
        this.loadRouteConfig();
    }

    private void loadRouteConfig() {
        List<RouteDefinition> routeList = routeService.getRouteList();
        log.info("----------持久层额外路由数量:{}---------",routeList.size());
        routeList.forEach(x -> routeDefinitionWriter.save(Mono.just(x)).subscribe());
        publishRouteEvent();
    }


    /**
     * 把自定义请求模型转换为
     *
     * @param form
     * @return
     */
    private RouteDefinition convert(RouteForm form) {
        RouteDefinition definition = new RouteDefinition();
        definition.setId(form.getId());
        definition.setOrder(form.getOrder());
        //设置断言
        List<PredicateDefinition> predicateDefinitions = form.getPredicates().stream()
                .distinct().map(predicateInfo -> {
                    PredicateDefinition predicate = new PredicateDefinition();
                    predicate.setArgs(predicateInfo.getArgs());
                    predicate.setName(predicateInfo.getName());
                    return predicate;
                }).collect(Collectors.toList());
        definition.setPredicates(predicateDefinitions);

        // 设置过滤
        List<FilterDefinition> filterList = form.getFilters().stream().distinct().map(x -> {
            FilterDefinition filter = new FilterDefinition();
            filter.setName(x.getName());
            filter.setArgs(x.getArgs());
            return filter;
        }).collect(Collectors.toList());
        definition.setFilters(filterList);
        // 设置URI,判断是否进行负载均衡
        URI uri;
        if (form.getUri().startsWith("http")) {
            uri = UriComponentsBuilder.fromHttpUrl(form.getUri()).build().toUri();
        } else {
            uri = URI.create(form.getUri());
        }
        definition.setUri(uri);
        return definition;
    }


    /**
     * 数据落库
     */
    public void enduranceRule(String name, String description, RouteDefinition definition) {
        String id = definition.getId();
        List<PredicateDefinition> predicates = definition.getPredicates();
        List<FilterDefinition> filters = definition.getFilters();
        int order = definition.getOrder();
        URI uri = definition.getUri();
        RouteInfo routeInfo = new RouteInfo();
        routeInfo.setName(name);
        routeInfo.setRouteId(id);
        routeInfo.setUri(uri.toString());
        routeInfo.setPredicates(JSON.toJSONString(predicates));
        routeInfo.setFilters(JSON.toJSONString(filters));
        routeInfo.setEnabled(true);
        routeInfo.setDescription(description);
        routeInfo.setOrderNum(order);
        routeInfo.setDeleteFlag(false);
        RouteInfo one = routeService.getOne(buildWrapper(id));
        if (one == null) {
            routeService.save(routeInfo);
        } else {
            routeInfo.setId(one.getId());
            routeService.updateById(routeInfo);
        }

    }
}