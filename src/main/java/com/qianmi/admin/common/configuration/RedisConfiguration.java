package com.qianmi.admin.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;

@Configuration
public class RedisConfiguration {

    @Bean
    public RedisSerializer jacksonSerializer(){
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean
    public RedisSerializer stringSerializer(){
        return new StringRedisSerializer();
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setDefaultSerializer(jacksonSerializer());
        template.setKeySerializer(stringSerializer());
        template.setValueSerializer(jacksonSerializer());
        template.setHashKeySerializer(stringSerializer());
        template.setHashValueSerializer(jacksonSerializer());
        return template;
    }
}

