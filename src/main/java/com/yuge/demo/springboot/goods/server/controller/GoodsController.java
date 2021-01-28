package com.yuge.demo.springboot.goods.server.controller;

import com.yuge.demo.springboot.goods.server.entity.Goods;
import com.yuge.demo.springboot.goods.server.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *     商品Demo
 * </p>
 *
 * @author: yuge
 * @since: 2020-06-04
 **/
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @PostMapping("")
    public String add(Goods goods) {
        int r = goodsService.insert(goods);
        return String.valueOf(r);
    }

//    @PostMapping("/")
//    public String add2(Goods goods) {
//        int r = goodsService.insert(goods);
//        return String.valueOf(r);
//    }

    @GetMapping("/{id}")
    public Goods get(@PathVariable Long id) {
        Goods goods = goodsService.selectByPrimaryKey(id);
        return goods;
    }

    @GetMapping("")
    public List<Goods> getAll() {
        return goodsService.findByAll();
    }

    @PutMapping("")
    public int update(Goods goods) {
        return goodsService.updateByPrimaryKeySelective(goods);
    }

}
