package com.yuge.demo.springboot.api.goods.service;

import com.yuge.demo.springboot.api.goods.entity.Goods;

import java.util.List;

public interface GoodsService{


    int deleteByPrimaryKey(Long id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);

    List<Goods> findByAll();

}
