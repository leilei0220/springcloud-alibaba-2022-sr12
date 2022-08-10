package com.leilei.gateway.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author lei
 * @create 2022-04-12 11:27
 * @desc 反应式编程模型测试
 **/
@RestController
@RequestMapping("/reactive")
public class ReactiveController {

    /**
     *  Mono  实现发布者 Publisher，并返回 0 或 1 个元素。
     *  在 WebFlux 接口中，请求不会被阻塞（我这里模拟了耗时2s），所以服务端的接口耗时为 0
     * @return
     */
    @GetMapping("/mono")
    public Mono<String> hello() {
        long start = System.currentTimeMillis();
        Mono<String> hello = Mono.fromSupplier(this::getHelloStr);
        System.out.println("线程:"+Thread.currentThread().getName()+"接口耗时：" + (System.currentTimeMillis() - start));
        return hello;
    }

    private String getHelloStr() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "mono";
    }

    @GetMapping(value = "/flux",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    // @GetMapping(value = "/flux")
    // @GetMapping(value = "/flux",produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<String> flux() {
        long start = System.currentTimeMillis();
        Flux<String> map = Flux.fromArray(new String[]{"疲劳报警", "非法驾驶", "超速", "异动"}).map(s -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "adas：" + s;
        });

        System.out.println("线程:"+Thread.currentThread().getName()+"接口耗时：" + (System.currentTimeMillis() - start));
        return map;
    }
}
