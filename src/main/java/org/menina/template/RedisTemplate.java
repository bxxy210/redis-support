package org.menina.template;

import redis.clients.jedis.JedisPool;

/**
 * Created by meninaChimp on 2016/9/18 0018.
 */
public class RedisTemplate {

    private JedisPool jedispool;

    public RedisTemplate(JedisPool jedispool){
        this.jedispool = jedispool;
    }


}
