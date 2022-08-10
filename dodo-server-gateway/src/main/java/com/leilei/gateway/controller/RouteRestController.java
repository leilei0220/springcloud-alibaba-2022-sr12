package com.leilei.gateway.controller;

import com.leilei.gateway.entity.Result;
import com.leilei.gateway.entity.form.RouteForm;
import com.leilei.gateway.service.DynamicRouteService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lei
 * @create 2022-04-11 17:06
 * @desc
 **/
@RestController
@RequestMapping("/api/route")
public class RouteRestController {

    private final DynamicRouteService dynamicRouteService;

    public RouteRestController(DynamicRouteService dynamicRouteService) {
        this.dynamicRouteService = dynamicRouteService;
    }

    /**
     * 增加路由
     *
     * @param routeForm 路由模型
     *                  {
     *                  "name":"bilibili路由",
     *                  "description":"测试自定义路由信息",
     *                  "filters": [{
     *                  "name": "StripPrefix",
     *                  "args": {
     *                  "_genkey_0": "1"
     *                  }
     *                  }],
     *                  "id": "test-route",
     *                  "uri": "https://www.bilibili.com/anime/?spm_id_from=333.1007.0.0",
     *                  "order": 2,
     *                  "predicates": [{
     *                  "name": "Path",
     *                  "args": {
     *                  "_genkey_0": "/b3",
     *                  "_genkey_1": "/b2"
     *                  }
     *                  }]
     *                  }
     * @return
     */
    @PostMapping
    public Result<Boolean> add(@RequestBody RouteForm routeForm) {
        return this.dynamicRouteService.add(routeForm);
    }


    /**
     * 更新路由
     * {
     * "name":"bilibili路由",
     * "description":"测试自定义路由信息修改,去掉一个path",
     * "filters": [{
     * "name": "StripPrefix",
     * "args": {
     * "_genkey_0": "1"
     * }
     * }],
     * "id": "test-route",
     * "uri": "https://www.bilibili.com/anime/?spm_id_from=333.1007.0.0",
     * "order": 2,
     * "predicates": [{
     * "name": "Path",
     * "args": {
     * "_genkey_0": "/b3",
     * }
     * }]
     * }
     *
     * @param routeForm 路由模型
     * @return
     */
    @PutMapping
    public Result<Boolean> update(@RequestBody RouteForm routeForm) {
        return this.dynamicRouteService.update(routeForm);
    }

    /**
     * 删除路由
     *
     * @param id 路由Id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable String id) {
        return this.dynamicRouteService.delete(id);
    }

    @GetMapping("/flush")
    public Result<Boolean> flush() {
        return this.dynamicRouteService.flushRoute();
    }
}