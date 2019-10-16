package com.yuge.demo.springboot.api.test.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 测试 Controller
 * </p>
 *
 * @author yuge
 * @since 2019-10-16
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    /**
     * 测试请求阻塞
     * @return
     */
    @PostMapping("xxx")
    public String xxx() {
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "success";
    }

}
