package com.yuge.demo.springboot.core.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <p>
 *     Redis配置
 * </p>
 *
 * @author: yuge
 * @since: 2020-06-05
 **/

@Configuration
public class RedisConfig {

    @Bean
    RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // JSON 序列化器
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        // 将Key序列化为字符串
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        // 将Value序列化为json
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setHashValueSerializer(new JdkSerializationRedisSerializer());
        return template;
    }

}
