package com.yuge.demo.springboot.core.util;

import com.yuge.demo.springboot.goods.server.entity.Goods;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheUtilsTest {

    @Autowired
    private CacheUtils cacheUtils;

    @Test
    public void expire() {
    }

    @Test
    public void getExpire() {
        long expire = cacheUtils.getExpire("xxx");
        System.out.println(expire);
        long expire2 = cacheUtils.getExpire("1");
        System.out.println(expire2);
    }

    @Test
    public void hasKey() {
    }

    @Test
    public void del() {
    }

    @Test
    public void get() {
        Goods d = (Goods) cacheUtils.get("goods:1");
        System.out.println(d);
        System.out.println(d.getAddress());
    }

    @Test
    public void set() {
        Assert.assertTrue(cacheUtils.set("1", "2"));
        Assert.assertTrue(cacheUtils.set("2", "1"));
        Goods goods = new Goods();
        goods.setId(1L);
        goods.setName("不知");
        goods.setAddress("不知不知");
        cacheUtils.set("goods:1", goods);
    }

    @Test
    public void set1() {
    }

    @Test
    public void incr() {
    }

    @Test
    public void decr() {
    }

    @Test
    public void hget() {
    }

    @Test
    public void hmget() {
    }

    @Test
    public void hmset() {
    }

    @Test
    public void hmset1() {
    }

    @Test
    public void hset() {
    }

    @Test
    public void hset1() {
    }

    @Test
    public void hdel() {
    }

    @Test
    public void hHasKey() {
    }

    @Test
    public void hincr() {
    }

    @Test
    public void hdecr() {
    }

    @Test
    public void sGet() {
    }

    @Test
    public void sHasKey() {
    }

    @Test
    public void sSet() {
    }

    @Test
    public void sSet1() {
    }

    @Test
    public void sGetSetSize() {
    }

    @Test
    public void setRemove() {
    }

    @Test
    public void lGet() {
    }

    @Test
    public void lGetListSize() {
    }

    @Test
    public void lGetIndex() {
    }

    @Test
    public void lSet() {
    }

    @Test
    public void lSet1() {
    }

    @Test
    public void lSetList() {
    }

    @Test
    public void lSetList1() {
    }

    @Test
    public void lUpdateIndex() {
    }

    @Test
    public void lRemove() {
    }
}