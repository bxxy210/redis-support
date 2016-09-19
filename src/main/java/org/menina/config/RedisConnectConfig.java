package org.menina.config;

import lombok.extern.slf4j.Slf4j;
import org.menina.args.RedisConnectArgs;
import org.menina.template.RedisTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by meninaChimp on 2016/9/19 0019.
 */
@Component
@Slf4j
public class RedisConnectConfig {

    private RedisConnectArgs redisConnectArgs;

    @Bean
    public RedisTemplate redisTemplate() {
        this.readFormRedisProperties();
        return new RedisTemplate(jedisPool());
    }

    private void readFormRedisProperties(){
        InputStream input = null;
        input = this.getClass().getClassLoader().getResourceAsStream("redis.properties");
        if(input == null){
            log.warn("not found redis.properties, the defaultRedis.properties will be uesd");
            input = this.getClass().getClassLoader().getResourceAsStream("redisDefault.properties");
        }

        Properties properties = new Properties();
        try {
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }

        try {
            input.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        this.redisConnectArgs = new RedisConnectArgs();
        this.redisConnectArgs.setHost(properties.getProperty("redis.host", "127.0.0.1"));
        this.redisConnectArgs.setPort(Integer.parseInt(properties.getProperty("redis.port", "6379")));
        this.redisConnectArgs.setPassword(properties.getProperty("redis.pass", ""));
        this.redisConnectArgs.setTimeout(Integer.parseInt(properties.getProperty("redis.timeout", "0")));
        this.redisConnectArgs.setMaxActive(Integer.parseInt(properties.getProperty("redis.maxActive", "1000")));
        this.redisConnectArgs.setMaxIdle(Integer.parseInt(properties.getProperty("redis.maxIdle", "0")));
        this.redisConnectArgs.setMaxWait(Integer.parseInt(properties.getProperty("redis.maxWait", "0")));
        this.redisConnectArgs.setMinEvictableIdleTimeMillis(Integer.parseInt(properties.getProperty("redis.minEvictableIdleTimeMillis", "3000")));
        this.redisConnectArgs.setTestOnBorrow(Boolean.parseBoolean(properties.getProperty("redis.testOnBorrow", "true")));
        this.redisConnectArgs.setTestOnCreate(Boolean.parseBoolean(properties.getProperty("redis.testOnCreate", "false")));
        this.redisConnectArgs.setEncode(properties.getProperty("redis.encode", "UTF-8"));
        this.redisConnectArgs.setTimeBetweenEvictionRunsMillis(Integer.parseInt(properties.getProperty("redis.timeBetweenEvictionRunsMillis", "3000")));
        this.redisConnectArgs.setDb(Integer.parseInt(properties.getProperty("redis.db", "0")));
        this.redisConnectArgs.setUnlock(Boolean.parseBoolean(properties.getProperty("redis.unlock", "false")));
    }

    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(this.redisConnectArgs.getMaxActive());
        jedisPoolConfig.setMaxIdle(this.redisConnectArgs.getMaxIdle());
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(this.redisConnectArgs.getTimeBetweenEvictionRunsMillis());
        jedisPoolConfig.setMinEvictableIdleTimeMillis(this.redisConnectArgs.getMinEvictableIdleTimeMillis());
        return jedisPoolConfig;
    }

    private JedisPool jedisPool() {
        JedisPool jedisPool = new JedisPool(
                jedisPoolConfig(),
                this.redisConnectArgs.getHost(),
                this.redisConnectArgs.getPort(),
                this.redisConnectArgs.getTimeout(),
                "".equals(this.redisConnectArgs.getPassword()) ? null : this.redisConnectArgs.getPassword(),
                this.redisConnectArgs.getDb());
        return jedisPool;
    }
}
