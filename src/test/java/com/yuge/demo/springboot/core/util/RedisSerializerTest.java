package com.yuge.demo.springboot.core.util;

import com.yuge.demo.springboot.goods.server.entity.Goods;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试各种Redis序列化性能
 *
 * 两个指标：序列化时间 以及 反序列化时间， 序列化长度
 *
 * 推荐使用 Jackson2JsonRedisSerializer；
 * 序列化时间 以及 反序列化时间: Jackson2JsonRedisSerializer序列化、反序列化时间远小于JdkSerializationRedisSerializer；
 * 序列化长度： 序列化整个List时，JdkSerializationRedisSerializer序列化长度相对较小；
 *              遍历序列List时，JdkSerializationRedisSerializer的累加序列长度相对较大；（每个序列化结果都包含整个类的定义、引用类的定义）
 * @author: yuge
 * @date: 2021-01-15
 **/
public class RedisSerializerTest {

    /**
     * JDK序列化为二进制； 对象需实现Serializable接口
     */
    public static final JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();

    /**
     * Jackson序列化为json；
     */
    public static final Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

    @Test
    public void serializerTest() {
        // 第一次执行时间存在异常，可能和类按需加载有关；
        System.out.println("===排除第一执行===");
        serializerTest(1);
        System.out.println("===以下数据为准===");
        serializerTest(1);
        serializerTest(10);
        serializerTest(100);
        serializerTest(1000);
        serializerTest(10000);
        serializerTest(100000);
        serializerTest(1000000);
    }

    public void serializerTest(int size) {
        List<Goods> dataList = goodsData(size);
        System.out.printf("数据量：%s \n", size);
        // 遍历每一项，进行序列化；序列化次数：dataList#size()
        redisSerializerEachTest(jdkSerializationRedisSerializer, dataList);
        redisSerializerEachTest(jackson2JsonRedisSerializer, dataList);
        // 将List进行序列化；序列化次数：1次
        redisSerializerAllTest(jdkSerializationRedisSerializer, dataList);
        redisSerializerAllTest(jackson2JsonRedisSerializer, dataList);
    }

    /**
     * 遍历每一项，进行序列化；序列化次数：dataList#size()
     *
     * @param redisSerializer
     * @param dataList
     */
    public void redisSerializerEachTest(RedisSerializer redisSerializer, List<Goods> dataList) {

        List<byte[]> bytesList = new ArrayList<>();
        // 序列化
        TimeTest serializeTime = new TimeTest().start();
        AtomicInteger length = new AtomicInteger();
        dataList.forEach(data -> {
            byte[] bytes = redisSerializer.serialize(data);
            length.addAndGet(bytes.length);
            bytesList.add(bytes);
        });
        serializeTime.end();

        // 反序列化
        TimeTest deserializeTime = new TimeTest().start();
        bytesList.forEach(bytes -> {
            redisSerializer.deserialize(bytes);
        });
        deserializeTime.end();

        System.out.printf("%s 遍历List序列化；序列化时间：%s, 反序列化时间：%s, 序列化后byte长度：%s \n",
                redisSerializer.getClass().getSimpleName(),
                serializeTime.getExecuteTime(),
                deserializeTime.getExecuteTime(),
                length);
    }

    public void redisSerializerAllTest(RedisSerializer redisSerializer, List<Goods> dataList) {
        List<byte[]> bytesList = new ArrayList<>();
        // 序列化
        TimeTest serializeTime = new TimeTest().start();
        byte[] bytes = redisSerializer.serialize(dataList);
        serializeTime.end();

        // 反序列化
        TimeTest deserializeTime = new TimeTest().start();
        redisSerializer.deserialize(bytes);
        deserializeTime.end();

        System.out.printf("%s 序列化List；序列化时间：%s, 反序列化时间：%s, 序列化后byte长度：%s \n",
                redisSerializer.getClass().getSimpleName(),
                serializeTime.getExecuteTime(),
                deserializeTime.getExecuteTime(),
                bytes.length);
    }

    public List<Goods> goodsData(int size) {
        List<Goods> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Goods goods = new Goods();
            goods.setId((long) i);
            goods.setAddress(RandomStringUtils.random(10));
            goods.setName(RandomStringUtils.random(5));
            list.add(goods);
        }
        return list;
    }
}
