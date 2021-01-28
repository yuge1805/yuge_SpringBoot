package com.yuge.demo.springboot.goods.server.mapper;

import com.yuge.demo.springboot.goods.server.entity.Goods;

import java.util.List;

public interface GoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);

    List<Goods> findByAll();

    List<Goods> selectByName(String name);
}