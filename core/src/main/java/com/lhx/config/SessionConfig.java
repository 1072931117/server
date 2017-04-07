package com.lhx.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieHttpSessionStrategy;
import org.springframework.session.web.http.SessionRepositoryFilter;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 该@EnableRedisHttpSession注释创建Spring Bean与名springSessionRepositoryFilter实现过滤。
 * 该过滤器是什么负责更换的HttpSession由春季会议进行备份的实现。在这种情况下，Spring Session由Redis支持。
 * 我们创建一个RedisConnectionFactory将Spring Session连接到Redis Server的连接。
 * Created by lhx on 2017/3/28.
 */
@EnableRedisHttpSession
@Import(SessionConfig.SessionLoader.class)
@PropertySource("classpath:/dev/properties/redis-base.properties")
public class SessionConfig {
    @PostConstruct
    public void init(){
        System.out.println("------------------SessionConfig--------------------");
    }

    @Resource(name = "connectionFactory")
    private JedisConnectionFactory connectionFactory;

    @Autowired
    private StringRedisSerializer stringRedisSerializer;

    @Bean
    RedisTemplate sessionRedisTemplate(){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(stringRedisSerializer);
        return redisTemplate;
    }

    @Bean
    SessionRepositoryFilter springSessionRepositoryFilter(){
        SessionRepositoryFilter sessionRepositoryFilter = new SessionRepositoryFilter(
                new RedisOperationsSessionRepository(connectionFactory));
        CookieHttpSessionStrategy cookieHttpSessionStrategy = new CookieHttpSessionStrategy();
        cookieHttpSessionStrategy.setCookieName("lhxSession");
        sessionRepositoryFilter.setHttpSessionStrategy(cookieHttpSessionStrategy);
        return sessionRepositoryFilter;
    }

    static class SessionLoader{
        @Autowired
        private Environment env;
        /**
         * 坑，有必要给connectionFactory bean起个名字
         * 因为RedisHttpSessionConfiguration配置中redisMessageListenerContainer Bean构造注入connectionFactory
         */
        @Bean(name = "connectionFactory")
        public JedisConnectionFactory connectionFactory(JedisPoolConfig jedisPoolConfig){
            JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
            jedisConnectionFactory.setHostName(env.getProperty("redis_host"));
            jedisConnectionFactory.setPort(Integer.valueOf(env.getProperty("redis_port")));
            jedisConnectionFactory.setPassword(env.getProperty("redis_pwd"));
            jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
            jedisConnectionFactory.setDatabase(8);
            jedisConnectionFactory.setUsePool(true);
            return jedisConnectionFactory;
        }

        @Bean
        public StringRedisSerializer stringRedisSerializer(){
            return new StringRedisSerializer();
        }
    }
}
