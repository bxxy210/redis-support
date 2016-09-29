package org.menina.template;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by meninaChimp on 2016/9/18 0018.
 */
@Slf4j
public class RedisTemplate {

    private JedisPool jedispool;

    private RedisTemplate(){}

    public RedisTemplate(JedisPool jedispool){
        this.jedispool = jedispool;
    }

    public <T extends Serializable> T redisInvoke(String methodName, Object... args) {
        Jedis jedis = jedispool.getResource();
        T result = null;
        try{
            Class[] argsType = new Class[args.length];
            for(int i = 0; i< args.length; i++){
                argsType[i] = args[i].getClass();
            }

            Method method = jedis.getClass().getDeclaredMethod(methodName, argsType);
            result = (T)method.invoke(jedis, args);
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            log.error(e.getMessage(), e);
        }finally {
            jedis.close();
            return result;
        }
    }
}
