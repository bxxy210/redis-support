package org.menina;

/**
 * Created by meninaChimp on 2016/9/19 0019.
 */
public interface RedisSupport {

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
     * 指定键在传入秒数后超时过期
     * @param key
     * @param seconds
     * @return 1 设置成功
     *          0 设置失败
     */
    Long expire(String key, int seconds);

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

}
