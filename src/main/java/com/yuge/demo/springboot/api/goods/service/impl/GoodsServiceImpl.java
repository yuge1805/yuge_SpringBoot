package com.yuge.demo.springboot.api.goods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.yuge.demo.springboot.api.goods.mapper.GoodsMapper;
import com.yuge.demo.springboot.api.goods.entity.Goods;
import com.yuge.demo.springboot.api.goods.service.GoodsService;

import java.util.List;

/**
 * 缓存Demo : https://baijiahao.baidu.com/s?id=1662491985073975957&wfr=spider&for=pc
 */
@CacheConfig(cacheNames = "goods")
@Service
public class GoodsServiceImpl implements GoodsService{

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return goodsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Goods record) {
        int result = goodsMapper.insert(record);
        return result;
    }

    @Override
    public int insertSelective(Goods record) {
        return goodsMapper.insertSelective(record);
    }

    @Cacheable(key = "#id")
//    @Cacheable("goods")
    @Override
    public Goods selectByPrimaryKey(Long id) {
        return goodsMapper.selectByPrimaryKey(id);
    }

    @Caching(evict = {
            @CacheEvict(key = "#record.id"),    // 删除Model缓存
            @CacheEvict(cacheNames = "goodsList", allEntries = true) // 删除列表缓存
    })
    @Override
    public int updateByPrimaryKeySelective(Goods record) {
        return goodsMapper.updateByPrimaryKeySelective(record);
    }

    @CacheEvict(key = "#record.id")
    @Override
    public int updateByPrimaryKey(Goods record) {
        return goodsMapper.updateByPrimaryKey(record);
    }

    @Cacheable(cacheNames = "goodsList")
    @Override
    public List<Goods> findByAll() {
        return goodsMapper.findByAll();
    }
}
