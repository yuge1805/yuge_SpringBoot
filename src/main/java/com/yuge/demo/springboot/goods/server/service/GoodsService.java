package com.yuge.demo.springboot.goods.server.service;

import com.github.pagehelper.Page;
import com.yuge.demo.springboot.goods.common.param.GoodsQueryParam;
import com.yuge.demo.springboot.goods.server.entity.Goods;

import java.util.List;

public interface GoodsService{


    int deleteByPrimaryKey(Long id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);

    List<Goods> findByAll();

    /**
     * 分页查询
     *
     * @param param
     * @return
     */
    List<Goods> selectByPage(GoodsQueryParam param);

    /**
     * 根据名字查询
     *
     * @param name
     * @return
     */
    Goods selectByName(String name);
}
