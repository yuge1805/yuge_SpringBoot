package com.yuge.demo.springboot.core.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Redis配置
 * </p>
 *
 * @author: yuge
 * @since: 2020-06-05
 **/
@Configuration
public class RedisConfig {

    @Bean
    RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        // 将Key序列化为字符串
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        // 将Value序列化为json
//        template.setValueSerializer(new JdkSerializationRedisSerializer());
//        template.setHashValueSerializer(new JdkSerializationRedisSerializer());
        template.setValueSerializer(redisSerializer());
        template.setHashValueSerializer(redisSerializer());
        return template;
    }

    /**
     * 缓存管理器
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        // 缓存配置
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                // Key序列化为字符串
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                // Value序列化为JSON
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer()));
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer()))
//                .entryTtl(Duration.ofSeconds(60))
//        // 禁止缓存null值； 缓存为NULL时则报错；
//        cacheConfig.disableCachingNullValues();

//        // 自定义每种类型缓存
//        Map<String, RedisCacheConfiguration> initCacheConfig = new HashMap<>();
//        initCacheConfig.put("goods", getRedisCacheConfiguration(60));
//        initCacheConfig.put("goodsList", getRedisCacheConfiguration(60));
//        return IngRedisCacheManager.builder(factory)
//                .cacheDefaults(cacheConfig)
//                .withInitialCacheConfigurations(initCacheConfig)
//                .build();
        return new IngRedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(factory), cacheConfig);
    }

    @Bean
    public RedisSerializer redisSerializer() {
        // JSON 序列化器
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }

}
