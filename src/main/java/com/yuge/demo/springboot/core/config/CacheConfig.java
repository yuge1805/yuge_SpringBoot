package com.yuge.demo.springboot.core.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *     Spring Cache 配置
 * </p>
 *
 * @author: yuge
 * @since: 2020-06-05
 **/
@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

    /**
     * Key生成策略
     * @Cacheable, @CacheEvict等注解未指定Key, 使用该策略；
     * @return
     */
    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuffer sb = new StringBuffer();
                sb.append(target.getClass().getSimpleName());
                sb.append(":");
                sb.append(method.getName());
                sb.append("#");
                for (Object obj : params) {
                    sb.append(obj.toString());
                    sb.append(",");
                }
                return sb.substring(0, sb.length() - 1);
            }
        };
    }

}
