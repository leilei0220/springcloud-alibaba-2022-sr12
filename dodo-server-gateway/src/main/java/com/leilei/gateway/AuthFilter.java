package com.leilei.gateway;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.leilei.gateway.entity.Result;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lei
 * @create 2022-04-07 17:19
 * @desc 权限过滤器
 **/
@Component
public class AuthFilter implements GlobalFilter, Ordered {
    Set<String> blacklist = new HashSet<String>() {
        {
            add("/app-base-center/nacos/discovery");
        }
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (!blacklist.contains(path)) {
            //当前放行,交由下个过滤器过滤
            return chain.filter(exchange);
        }
        String token = exchange.getRequest().getHeaders().getFirst("x-request-token");
        //按业务验证对比token
        if (token == null || token.replace(" ","").length() < 1) {
            byte[] bytes = JSON.toJSONString(new Result<>(-1, "无权限查看此接口!", null), SerializerFeature.WriteMapNullValue)
                    .getBytes(StandardCharsets.UTF_8);
            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            //响应出去
            return response.writeWith(Flux.just(buffer));
        }
        System.out.println(exchange.getRequest().getMethod() + "==" + exchange.getRequest().getURI().toString());
        //当前放行,交由下个过滤器过滤
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 100;
    }
}
