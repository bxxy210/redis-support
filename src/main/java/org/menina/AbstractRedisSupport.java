package org.menina;

import lombok.extern.slf4j.Slf4j;
import org.menina.template.RedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by meninaChimp on 2016/9/19 0019.
 */
@Slf4j
public abstract class AbstractRedisSupport implements IRedisSupport{

    @Autowired
    private RedisTemplate redisTemplate;

    public String get(String key) {
        if(key.equals("")){
            return "";
        }

        return redisTemplate.redisInvoke("get", key);
    }
}
