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
    @RequestMapping("xxx")
    public String xxx(String p) {
        System.out.println("？？？？？");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("completed!");
        return "success xxx";
    }

}
