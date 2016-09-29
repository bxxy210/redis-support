package org.menina;

import lombok.extern.slf4j.Slf4j;
import org.menina.template.RedisTemplate;
import org.menina.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by meninaChimp on 2016/9/19 0019.
 */
@Slf4j
public abstract class AbstractRedisSupport implements RedisSupport {

    @Autowired
    private RedisTemplate redisTemplate;

    public String get(String key) {
        if(StringUtils.isEmpty(key)){
            return key;
        }

        String result = redisTemplate.redisInvoke("get", key);
        return result;
    }

    @Override
    public void set(String key, String value) {
        redisTemplate.redisInvoke("set", key, value);
    }

    @Override
    public void expireAt(String key, long unixTimeSeconds) {
        if(unixTimeSeconds*1000 <= System.currentTimeMillis()){
            return;
        }

        redisTemplate.redisInvoke("expireAt", key, unixTimeSeconds);
    }

    @Override
    public Long expire(String key, int seconds) {
        return redisTemplate.redisInvoke("expire", key, seconds);
    }

    @Override
    public Long ttl(String key) {
        return redisTemplate.redisInvoke("ttl", "key");
    }

    @Override
    public Long del(String key) {
        return redisTemplate.redisInvoke("del", "key");
    }

    @Override
    public void persist(String key) {
        redisTemplate.redisInvoke("persist", "key");
    }
}
