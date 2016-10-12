package org.menina;

import org.menina.exception.CodisNotSupportException;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by meninaChimp on 2016/9/19 0019.
 */
@Component
public class RedisClient extends AbstractRedisSupport{

    @Override
    public Set<String> keys(String pattern) throws CodisNotSupportException {
        return redisTemplate.redisInvoke("keys", pattern);
    }
}
