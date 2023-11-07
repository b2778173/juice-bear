package com.jb.framework.config;


import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "rate-limit", name = "enabled", havingValue = "true")
public class RateLimitRedissonConfig {

    @Bean("rateLimitProperties")
    @ConfigurationProperties(prefix = "redisson.server.rate-limit")
    public RedissonProperties getProperties() {
        return new RedissonProperties();
    }

    @Bean("rateLimitConfig")
    public Config rateLimitRedissonConfigFactory(@Qualifier("rateLimitProperties") RedissonProperties redissonProperties) {
        RedissonConfigFactory redissonConfigFactory = new RedissonConfigFactory();
        return redissonConfigFactory.createRedissonProperties(redissonProperties);
    }

    @Bean("rateLimitClient")
    public RedissonClient rateLimitRedissonClient(@Qualifier("rateLimitConfig") Config config) {
        RedissonClient redissonClient = Redisson.create(config);
        log.info("创建 rateLimit redissonClient 客户端成功");
        return redissonClient;
    }

    @Bean(name = "rateLimitConnectionFactory")
    public RedissonConnectionFactory rateLimitRedissonConnectionFactory(@Qualifier("rateLimitClient") RedissonClient redissonClient) {
        return new RedissonConnectionFactory(redissonClient);
    }

    @Bean(name = "rateLimitRedisTemplate")
    public RedisTemplate rateLimitRedisTemplate(@Qualifier("rateLimitConnectionFactory") RedissonConnectionFactory redissonConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        KryoRedisSerializer<Object> kryoRedisSerializer = new KryoRedisSerializer<>(Object.class);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(kryoRedisSerializer);
        redisTemplate.setHashValueSerializer(kryoRedisSerializer);
        redisTemplate.setConnectionFactory(redissonConnectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean(name = "kryoRedisTemplate")
    public RedisTemplate redisTemplate(RedissonConnectionFactory redissonConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        KryoRedisSerializer<Object> kryoRedisSerializer = new KryoRedisSerializer<>(Object.class);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(kryoRedisSerializer);
        redisTemplate.setHashValueSerializer(kryoRedisSerializer);
        redisTemplate.setConnectionFactory(redissonConnectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean(name = "objectRedisTemplate")
    public RedisTemplate<String, Object> objectRedisTemplate(RedissonConnectionFactory redissonConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.setConnectionFactory(redissonConnectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


}
