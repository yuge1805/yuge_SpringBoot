package com.yuge.demo.springboot.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 缓存预热
 *
 * 容器启动完成之后，执行ApplicationRunner；已经可以接受到请求；
 *
 * @author: yuge
 * @date: 2021-01-29
 **/
@Slf4j
@Component
public class CachePreheat implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        Thread.sleep(60000L);
        log.info("预热完成了");
    }

    /**
     * 容器启动中，接受不到请求；
     *
     * @throws InterruptedException
     */
    @PostConstruct
    public void init() throws InterruptedException {
//        Thread.sleep(60000L);
        log.info("CachePreheat PostConstruct init. ");
    }
}
