package com.yuge.demo.springboot.core.util;

import com.yuge.demo.springboot.goods.server.entity.Goods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: yuge
 * @date: 2021-01-14
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTemplateTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void hasKey() {
        boolean result = redisTemplate.hasKey("1");
        System.out.println(result);
    }

    @Test
    public void expire() {
        Long expire = redisTemplate.getExpire("order::2");
        System.out.println(expire);
    }

    @Test
    public void opsForValue_increment() {
        Long result = redisTemplate.opsForValue().increment("in");
        System.out.println(result);
        Long r2 = redisTemplate.opsForValue().increment("in", 2);
        System.out.println(r2);
        // double自增存在问题
//        double d = redisTemplate.opsForValue().increment("in", 2.2);
    }

    @Test
    public void opsForValue_setIfPresent() {
        boolean result = redisTemplate.opsForValue().setIfPresent("order::1", "xxx");
        System.out.println(result);
        boolean result2 = redisTemplate.opsForValue().setIfPresent("order::1", "xxx");
        System.out.println(result2);
    }

    @Test
    public void opsForValue_setIfAbsent() {
        boolean result = redisTemplate.opsForValue().setIfAbsent("order::1", "xxx");
        System.out.println(result);
        boolean result2 = redisTemplate.opsForValue().setIfAbsent("order::1", "xxx");
        System.out.println(result2);
    }

    @Test
    public void opsForValue_set() throws InterruptedException {
        redisTemplate.opsForValue().set("order::2", "X2");
        Object result = redisTemplate.opsForValue().get("order::2");
        System.out.println(result);
        // class java.lang.String
        System.out.println(result.getClass());

        //TTL
        redisTemplate.opsForValue().set("order::2", "X22", 60, TimeUnit.SECONDS);
        Long expire = redisTemplate.getExpire("order::2");
        System.out.println(expire);

        // Object
        System.out.println("Object==========================");
        Goods goods = new Goods();
        goods.setId(3L);
        goods.setAddress("XXXXX");
        redisTemplate.opsForValue().set("good::3", goods);
        Goods object = (Goods) redisTemplate.opsForValue().get("good::3");
        System.out.println(object);
        System.out.println(object.getClass());

        // List
        System.out.println("List==========================");
        List<Goods> list = Arrays.asList(goods);
        redisTemplate.opsForValue().set("goodList", list);
        List<Goods> o = (List<Goods>) redisTemplate.opsForValue().get("goodList");
        System.out.println(o);
        System.out.println(o.getClass().getSimpleName());
    }

    @Test
    public void opsForValue_multiGet() {
        List<Object> list = redisTemplate.opsForValue().multiGet(Arrays.asList("goods::1", "goods::2"));
        System.out.println(list);
    }

    @Test
    public void execute() {
        // Lua脚本返回类型需要与ResultType一致，否则会报异常
        RedisScript<Long> redisScript = RedisScript.of("if redis.call('get', KEYS[1]) == ARGV[1] then " +
                "return redis.call('del', KEYS[1]) " +
                "else " +
                "return 0 " +
                "end", Long.class);
        List<String> list = new ArrayList<>();
        list.add("1");
        Long result = redisTemplate.execute(redisScript, list, 1);
        System.out.println(result);
    }

    public void opsForValue2() {
    }

}
