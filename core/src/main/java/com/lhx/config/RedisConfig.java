package com.lhx.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;

/**
 * 此配置主要为缓存系统使用
 * Created by lhx on 2017/3/28.
 */
@Configurable
@Import(RedisConfig.RedisLoader.class)
public class RedisConfig {
    @PostConstruct
    public void init(){
        System.out.println("------------------RedisConfig--------------------");
    }

    @Autowired
    JedisConnectionFactory jedisConnectionFactory;

    @Bean
    RedisTemplate redisTemplate(){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        return redisTemplate;
    }

    @SuppressWarnings("Duplicates")
    @Import(PoolLoader.class)
    static class RedisLoader{
        @Autowired
        Environment env;
        @Bean
        public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig){
            JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
            jedisConnectionFactory.setHostName(env.getProperty("redis_host"));
            jedisConnectionFactory.setPort(Integer.valueOf(env.getProperty("redis_port")));
            jedisConnectionFactory.setPassword(env.getProperty("redis_pwd"));
            jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
            return jedisConnectionFactory;
        }
    }

    static class PoolLoader {
        @Bean
        public JedisPoolConfig jedisPoolConfig(){
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxIdle(300);//最大能够保持idel状态的对象数
            jedisPoolConfig.setMaxTotal(60000);//最大分配的对象数
            jedisPoolConfig.setTestOnBorrow(true);//当调用borrow Object方法时，是否进行有效性检查
            return jedisPoolConfig;
        }
    }

}
