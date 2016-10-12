package org.menina;

import org.menina.exception.CodisNotSupportException;
import org.menina.utils.TimeUnit;
import redis.clients.jedis.Transaction;

import java.util.Set;

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
     * 键值对是否存在
     * @param key
     * @return
     */
    Boolean exists(String key);

    /**
     * 键的值后追加内容
     * @param key
     * @param value
     * @return
     */
    Long append(String key, String value);

    /**
     * 截取键的值返回, 可使用getrange方法替换
     * @param key
     * @param start
     * @param end
     * @return
     */
    String substr(String key, int start, int end);

    /**
     * 附带超时功能的保存键值对
     * @param key
     * @param seconds
     * @param value
     */
    void setex(String key, int seconds, String value);

    /**
     * 键的值的类型
     * @param key
     * @return
     */
    String type(String key);

    /**
     * 获取所有符合模式的键
     * db数据量大于1w请慎用（1.cpu消耗上升 2.Time Out）
     * @param pattern
     * @return
     * @throws CodisNotSupportException
     */
    Set<String> keys(String pattern) throws CodisNotSupportException;

    /**
     * hash操作
     */

    /**
     * 保存一个键-域-值对
     * @param key
     * @param field
     * @param value
     */
    void hset(String key, String field, String value);

    /**
     * 获取指定键，域下的值
     * @param key
     * @param field
     * @return
     */
    String hget(String key, String field);

    /**
     * 原子操作
     */

    /**
     * 键不存在时保存
     * @param key
     * @param value
     * @return 1 保存成功
     * 			0 键已存在，保存失败
     */
    Long setnx(String key, String value);

    /**
     * 键的值自减操作, 使用时请确保键的值是可转换为Number类型的, 减数为1
     * @param key
     * @return
     */
    Long decr(String key);

    /**
     * 键的值自减操作，使用时请确保键的值是可转换为Number类型的, 减数为number
     * @param key
     * @param number
     * @return
     */
    Long decrBy(String key, long number);

    /**
     * 键的值自增操作，使用时请确保键的值是可转换为Number类型的, 增数为1
     * @param key
     * @return
     */
    Long incr(String key);

    /**
     * 键的值自增操作，使用时请确保键的值是可转换为Number类型的, 增数为number
     * @param key
     * @param number
     * @return
     */
    Long incrBy(String key, long number);

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
