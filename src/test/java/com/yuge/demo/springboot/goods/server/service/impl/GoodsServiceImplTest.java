package com.yuge.demo.springboot.goods.server.service.impl;

import com.github.pagehelper.Page;
import com.yuge.demo.springboot.goods.common.param.GoodsQueryParam;
import com.yuge.demo.springboot.goods.server.entity.Goods;
import com.yuge.demo.springboot.goods.server.service.GoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsServiceImplTest {

    @Autowired
    private GoodsService goodsService;

    @Test
    public void deleteByPrimaryKey() {
    }

    @Test
    public void insert() {
    }

    @Test
    public void insertSelective() {
    }

    @Test
    public void selectByPrimaryKey() {
        Goods goods = goodsService.selectByPrimaryKey(1L);
        Goods goods2 = goodsService.selectByPrimaryKey(2L);
    }

    @Test
    public void updateByPrimaryKeySelective() {
    }

    @Test
    public void updateByPrimaryKey() {
        Goods goods = new Goods();
        goods.setId(1L);
        goods.setAddress(new Date().toString());
        goodsService.updateByPrimaryKey(goods);
    }

    @Test
    public void findByAll() {
        goodsService.findByAll();
    }

    @Test
    public void selectByPage() {
        GoodsQueryParam param = new GoodsQueryParam();
        param.setPageNo(1);
        param.setPageSize(2);
        List<Goods> resultList = goodsService.selectByPage(param);
        System.out.println(resultList);
    }

    @Test
    public void selectByName() {
        Goods goods = goodsService.selectByName("pa111");
        System.out.println(goods);

//        Goods goods1 = goodsService.selectByName("pa1");
//        System.out.println(goods1);
    }
}