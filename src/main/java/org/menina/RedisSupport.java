package org.menina;

import org.menina.utils.TimeUnit;
import redis.clients.jedis.Transaction;

/**
 * Created by meninaChimp on 2016/9/19 0019.
 */
public interface RedisSupport {

    /**
     * String操作
     */

    /**
     * 获取一个值
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 存一个键值对
     * @param key
     * @param value
     */
    void set(String key, String value);

    /**
     * 指定键在unix时间超时过期
     * @param key
     * @param unixTimeSeconds
     */
    void expireAt(String key, long unixTimeSeconds);

    /**
     * 指定键在传入时间后超时过期
     * @param key
     * @param time
     * @return 1 设置成功
     *          0 设置失败
     */
    Long expire(String key, int time, TimeUnit unit);

    /**
     * 键剩余存活毫秒数
     * @param key
     * @return
     */
    Long ttl(String key);

    /**
     * 删除一个键值对
     * @param key
     * @return
     */
    Long del(String key);

    /**
     * 持久化，移除键值对的过期时间
     * @param key
     * @return
     */
    void persist(String key);

    /**
     * 事务
     */

    /**
     * 监视键的值是否被其他线程改变
     * @param keys
     */
    void watch(String... keys);

    /**
     * 取消watch操作对所有键的监视，使用时请注意影响范围
     */
    void unwatch();

    /**
     * 开启一个事务
     * 执行事务请调用Transaction.exec()方法
     */
    Transaction multi();
}
