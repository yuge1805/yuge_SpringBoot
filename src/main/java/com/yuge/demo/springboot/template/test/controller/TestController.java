package com.yuge.demo.springboot.template.test.controller;

import com.yuge.demo.springboot.goods.server.entity.Goods;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

/**
 * <p>
 * 测试 Controller
 * </p>
 *
 * @author yuge
 * @since 2019-10-16
 */
@RestController
@RequestMapping("/template/test")
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

    /**
     * 测试oom
     * @return
     */
    @RequestMapping("oom")
    public String oom() {
        int i = 0;
        try {
            while (true) {
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(Goods.class);
                enhancer.setUseCache(false);
                enhancer.setCallback(new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        return methodProxy.invokeSuper(o, objects);
                    }
                });
                enhancer.create();
                i++;
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return "false";
        } finally {
            System.out.println("运行次数：" + i);
        }
    }

}
