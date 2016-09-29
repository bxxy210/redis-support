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
@Slf4j
@Component
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
            log.warn("not found redis.properties, the defaultRedis.properties will be load");
            input = this.getClass().getClassLoader().getResourceAsStream("redisDefault.properties");
        }

        Properties properties = new Properties();
        try {
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("exception occur when load redis properties", e);
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
        this.redisConnectArgs.setMaxWaitMills(Integer.parseInt(properties.getProperty("redis.maxWaitMills", "0")));
        this.redisConnectArgs.setMinEvictableIdleTimeMillis(Integer.parseInt(properties.getProperty("redis.minEvictableIdleTimeMillis", "3000")));
        this.redisConnectArgs.setTestOnBorrow(Boolean.parseBoolean(properties.getProperty("redis.testOnBorrow", "true")));
        this.redisConnectArgs.setTestOnCreate(Boolean.parseBoolean(properties.getProperty("redis.testOnCreate", "false")));
        this.redisConnectArgs.setEncode(properties.getProperty("redis.encode", "UTF-8"));
        this.redisConnectArgs.setTimeBetweenEvictionRunsMillis(Integer.parseInt(properties.getProperty("redis.timeBetweenEvictionRunsMillis", "3000")));
        this.redisConnectArgs.setDb(Integer.parseInt(properties.getProperty("redis.db", "0")));
        this.redisConnectArgs.setUnlock(Boolean.parseBoolean(properties.getProperty("redis.unlock", "false")));
        this.logRedisArgs(redisConnectArgs);
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

    private void logRedisArgs(RedisConnectArgs redisConnectArgs){
        log.info("redis.host:" + redisConnectArgs.getHost());
        log.info("redis.port:" + redisConnectArgs.getPort());
        log.info("redis.timeout:" + redisConnectArgs.getTimeout());
        log.info("redis.maxIdle:" + redisConnectArgs.getMaxIdle());
        log.info("redis.maxActive:" + redisConnectArgs.getMaxActive());
        log.info("redis.maxWaitMillis:" + redisConnectArgs.getMaxWaitMills());
        log.info("redis.timeBetweenEvictionRunsMillis:" + redisConnectArgs.getTimeBetweenEvictionRunsMillis());
        log.info("redis.minEvictableIdleTimeMillis:" + redisConnectArgs.getMinEvictableIdleTimeMillis());
        log.info("redis.testOnBorrow:" + redisConnectArgs.isTestOnBorrow());
        log.info("redis.testOnCreate:" + redisConnectArgs.isTestOnCreate());
        log.info("redis.encode:" + redisConnectArgs.getEncode());
        log.info("redis.unlock:" + redisConnectArgs.isUnlock());
        log.info("redis.db:" + redisConnectArgs.getDb());
    }
}
