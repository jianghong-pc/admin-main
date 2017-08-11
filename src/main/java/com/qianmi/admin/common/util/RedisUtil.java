package com.qianmi.admin.common.util;

import com.qianmi.admin.common.constants.Module;
import com.qianmi.admin.common.exception.AdminRuntimeException;
import com.qianmi.admin.common.exception.errorcode.BaseErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * redis操作包装
 *
 * @author apple
 */
@Configuration
public class RedisUtil {

    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    /**
     * hset一个字符串
     *
     * @param key   key
     * @param field field
     * @param value value
     * @return 0:值已设置过 1:值第一次设置
     * @throws AdminRuntimeException
     */
    public long hset(final String key, final String field, final String value) throws AdminRuntimeException {

        if (StringUtils.isBlank(key) || StringUtils.isBlank(field) || StringUtils.isBlank(value)) {
            logger.error("hset入参为空,key={},field={},value={}", key, field, value);
            throw new AdminRuntimeException(Module.COMMON, BaseErrorCode.PARAMETER_EXCEPTION, "入参为空");
        }

        long result;
        try {
            result = redisTemplate.execute(new RedisCallback<Long>() {

                @Override
                public Long doInRedis(RedisConnection redisConnection) {
                    long res = ((Jedis) redisConnection.getNativeConnection()).hset(key, field, value);
                    logger.debug("向redis里 hset, key={},field={},value={},result={}", key, field, value, res);
                    return res;
                }

            });
        } catch (Exception e) {
            logger.error("redis hset 异常。错误：{}", e.getMessage());
            throw new AdminRuntimeException(Module.COMMON, BaseErrorCode.REDIS_EXCEPTION);
        }

        return result;
    }

    /**
     * 删除hset的一个field of583_wj
     *
     * @param key   hset名
     * @param field hset的field
     * @return 是否删除成功
     * @throws AdminRuntimeException
     */
    public boolean hdel(final String key, final String field) throws AdminRuntimeException {

        if (StringUtils.isBlank(key) || StringUtils.isBlank(field)) {
            logger.error("hset入参为空,key={},field={}", key, field);
            throw new AdminRuntimeException(Module.COMMON, BaseErrorCode.PARAMETER_EXCEPTION, "入参为空");
        }

        try {
            return redisTemplate.execute(new RedisCallback<Boolean>() {

                @Override
                public Boolean doInRedis(RedisConnection redisConnection) {
                    Long result = ((Jedis) redisConnection.getNativeConnection()).hdel(key, field);
                    logger.debug("从redis hdel, key={},field={},result={}", key, field, result);
                    return 1 == result;
                }

            });

        } catch (Exception e) {
            logger.error("redis hdel 异常。错误：{}", e.getMessage());
            throw new AdminRuntimeException(Module.COMMON, BaseErrorCode.REDIS_EXCEPTION);
        }

    }

    /**
     * 获取hash map 对象 对应field的值
     * @param key
     * @param field
     * @return
     * @throws AdminRuntimeException
     */
    public String hget(final String key, final String field) throws AdminRuntimeException {

        if (StringUtils.isBlank(key) || StringUtils.isBlank(field)) {
            logger.error("hset入参为空,key={},field={}", key, field);
            throw new AdminRuntimeException(Module.COMMON, BaseErrorCode.PARAMETER_EXCEPTION, "入参为空");
        }

        try {
            return redisTemplate.execute(new RedisCallback<String>() {

                @Override
                public String doInRedis(RedisConnection redisConnection) {
                    String result = ((Jedis) redisConnection.getNativeConnection()).hget(key, field);
                    logger.debug("从redis hget, key={},field={},result={}", key, field, result);
                    return result;
                }

            });

        } catch (Exception e) {
            logger.error("redis hdel 异常。错误：{}", e.getMessage());
            throw new AdminRuntimeException(Module.COMMON, BaseErrorCode.REDIS_EXCEPTION);
        }

    }

    /**
     * 添加一个元素到list的末尾
     *
     * @param key   list名
     * @param value 值
     * @return list长度
     * @throws AdminRuntimeException
     */
    public long rpush(final String key, final String value) throws AdminRuntimeException {

        if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
            logger.error("rpush 入参为空,key={},value={}", key, value);
            throw new AdminRuntimeException(Module.COMMON, BaseErrorCode.PARAMETER_EXCEPTION, "入参为空");
        }

        long result;
        try {
            result = redisTemplate.execute(new RedisCallback<Long>() {

                @Override
                public Long doInRedis(RedisConnection redisConnection) {
                    long res = ((Jedis) redisConnection.getNativeConnection()).rpush(key, value);
                    logger.debug("向redis里 rpush, key={},value={},result={}", key, value, res);
                    return res;
                }

            });
        } catch (Exception e) {
            logger.error("redis rpush 异常。错误：{}", e.getMessage());
            throw new AdminRuntimeException(Module.COMMON, BaseErrorCode.REDIS_EXCEPTION);
        }

        return result;
    }

    public long rpush(final String key, final List<String> values) throws AdminRuntimeException {

        if (StringUtils.isBlank(key) || CollectionUtils.isEmpty(values)) {
            logger.error("rpush 入参为空,key={},value={}", key, values);
            throw new AdminRuntimeException(Module.COMMON, BaseErrorCode.PARAMETER_EXCEPTION, "入参为空");
        }

        long result;
        try {
            result = redisTemplate.execute(new RedisCallback<Long>() {

                @Override
                public Long doInRedis(RedisConnection redisConnection) {
                    String[] valueArray = values.toArray(new String[values.size()]);
                    long res = ((Jedis) redisConnection.getNativeConnection()).rpush(key, valueArray);
                    logger.debug("向redis里 rpush, key={},value={},result={}", key, valueArray, res);
                    return res;
                }

            });
        } catch (Exception e) {
            logger.error("redis rpush 异常。错误：{}", e.getMessage());
            throw new AdminRuntimeException(Module.COMMON, BaseErrorCode.REDIS_EXCEPTION);
        }

        return result;
    }

    /**
     * 从redis缓存中获取指定下标范围的list
     *
     * @param key   list key
     * @param startIndex 开始list 下标 从0开始
     * @param endIndex 结束list 下标  -1表示所有
     * @author zhangyue
     * @return List
     */
    public List<String> lrange(final String key, final long startIndex,final long endIndex) {

        if (StringUtils.isBlank(key) || startIndex < -1 || endIndex < -1) {
            logger.error("rpush 入参为空,key={},startIndex={},endIndex={}", key, startIndex,endIndex);
            throw new IllegalArgumentException("参数错误，key不能为空，startIndex和endIndex 必须大于或等于 -1");
        }

        List<String> result = Collections.emptyList();
        try {
            result = redisTemplate.execute(new RedisCallback<List<String>>() {
                @Override
                public List<String> doInRedis(RedisConnection redisConnection) {
                    List<String> res = ((Jedis) redisConnection.getNativeConnection()).lrange(key, startIndex, endIndex);
                    logger.debug("向redis里 rpush, key={},startIndex={},endIndex={},result={}", key, startIndex,endIndex,res);
                    return res;
                }
            });
        } catch (Exception e) {
            logger.error("redis rpush 异常。错误：{}", e.getMessage());
        }
        return result;
    }


    /**
     * 从redis中获取指定key的list对象
     * @param key
     */
    public List<String> lgetAll(String key) {
        return lrange(key,0,-1);
    }

    /**
     * 取list的第一个元素
     *
     * @param key list名
     * @return list的第一个元素
     * @throws AdminRuntimeException
     */
    public String lpop(final String key) throws AdminRuntimeException {

        if (StringUtils.isBlank(key)) {
            logger.error("redis lpop 入参为空,key={}", key);
            throw new AdminRuntimeException(Module.COMMON, BaseErrorCode.PARAMETER_EXCEPTION, "入参为空");
        }

        String result;
        try {
            result = redisTemplate.execute(new RedisCallback<String>() {

                @Override
                public String doInRedis(RedisConnection redisConnection) {
                    String res = ((Jedis) redisConnection.getNativeConnection()).lpop(key);
                    logger.debug("redis lpop, key={},result={}", key, res);
                    return res;
                }

            });
        } catch (Exception e) {
            logger.error("redis lpop 异常。错误：{}", e.getMessage());
            throw new AdminRuntimeException(Module.COMMON, BaseErrorCode.REDIS_EXCEPTION);
        }

        return result;
    }

    public Long llen(final String key) throws AdminRuntimeException {

        if (StringUtils.isBlank(key)) {
            logger.error("redis llen 入参为空,key={}", key);
            throw new AdminRuntimeException(Module.COMMON, BaseErrorCode.PARAMETER_EXCEPTION, "入参为空");
        }
        Long result = 0L;
        try {
            result = redisTemplate.execute(new RedisCallback<Long>() {

                @Override
                public Long doInRedis(RedisConnection redisConnection) {
                    Long res = ((Jedis) redisConnection.getNativeConnection()).llen(key);
                    logger.debug("redis llen, key={},result={}", key, res);
                    return res;
                }

            });
        } catch (Exception e) {
            logger.error("redis llen 异常。错误：{}", e.getMessage());
            throw new AdminRuntimeException(Module.COMMON, BaseErrorCode.REDIS_EXCEPTION);
        }
        return result;
    }

    /**
     * 设置key的自动超时时间，超时后自动删除 of583_wj。 GET,GETSET会清除超时时间，详见http://redis.io/commands/expire
     *
     * @param key     key
     * @param seconds 超时时间，单位秒
     * @return 是否设置成功
     * @throws AdminRuntimeException
     */
    public boolean expire(final String key, final int seconds) throws AdminRuntimeException {

        if (StringUtils.isBlank(key) || seconds < 0) {
            logger.error("redis expire 入参错误,key={},seconds={}", key, seconds);
            throw new AdminRuntimeException(Module.COMMON, BaseErrorCode.PARAMETER_EXCEPTION, "入参为空");
        }

        try {
            return redisTemplate.execute(new RedisCallback<Boolean>() {

                @Override
                public Boolean doInRedis(RedisConnection redisConnection) {
                    Long result = ((Jedis) redisConnection.getNativeConnection()).expire(key, seconds);
                    logger.debug("redis expire, key={},seconds={},result={}", key, seconds, result);
                    return 1 == result;
                }

            });

        } catch (Exception e) {
            logger.error("redis expire 异常。错误：{}", e.getMessage());
            throw new AdminRuntimeException(Module.COMMON, BaseErrorCode.REDIS_EXCEPTION);
        }
    }

    /**
     * 根据key获取value
     * @param key
     * @return
     * @throws AdminRuntimeException
     */
    public String get(final String key) throws AdminRuntimeException {
        if (StringUtils.isBlank(key)) {
            logger.error("redis lpop 入参为空,key={}", key);
            throw new AdminRuntimeException(Module.COMMON, BaseErrorCode.PARAMETER_EXCEPTION, "redis lpop 入参为空");
        }

        String result;
        try {
            result = redisTemplate.execute(new RedisCallback<String>() {

                @Override
                public String doInRedis(RedisConnection redisConnection) {
                    String res = ((Jedis) redisConnection.getNativeConnection()).get(key);

                    logger.debug("redis lpop, key={},result={},ip={},port={}", key, res,((Jedis) redisConnection.getNativeConnection()).getClient().getHost(),((Jedis) redisConnection.getNativeConnection()).getClient().getPort());
                    return res;
                }

            });
        } catch (Exception e) {
            logger.error("redis lpop 异常。错误：{},exception={}", e.getMessage(),e);
            throw new AdminRuntimeException(Module.COMMON, BaseErrorCode.REDIS_EXCEPTION);
        }

        return result;
    }


    /**
     * set一个字符串
     *
     * @param key   key
     * @param value value
     * @return 0:值已设置过 1:值第一次设置
     * @throws AdminRuntimeException
     * @author of583_wj
     */
    public String set(final String key, final String value) throws AdminRuntimeException {

        if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
            logger.error("gset入参为空,key={},field={},value={}", key, value);
            throw new AdminRuntimeException(Module.COMMON, BaseErrorCode.PARAMETER_EXCEPTION, "gset入参为空");
        }

        String result;
        try {
            result = redisTemplate.execute(new RedisCallback<String>() {

                @Override
                public String doInRedis(RedisConnection redisConnection) {
                    String res = ((Jedis) redisConnection.getNativeConnection()).getSet(key, value);
                    logger.debug("从redis里 set, key={},result={}", key, value, res);
                    return res;
                }
            });
        } catch (Exception e) {
            logger.error("redis set 异常。错误：{}", e.getMessage());
            throw new AdminRuntimeException(Module.COMMON, BaseErrorCode.REDIS_EXCEPTION);
        }

        return result;
    }


    /**
     * set一个字符串
     *
     * @param key   key
     * @param value value
     * @param loseTime 失效时间 单位秒
     * @return 返回old value
     * @throws AdminRuntimeException
     * @author of583_wj
     */
    public String set(final String key, final String value,final int loseTime) throws AdminRuntimeException {

        if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
            logger.error("gset入参为空,key={},field={},value={}", key, value);
            throw new AdminRuntimeException(Module.COMMON, BaseErrorCode.PARAMETER_EXCEPTION, "gset入参为空");
        }

        String result;
        try {
            result = redisTemplate.execute(new RedisCallback<String>() {

                @Override
                public String doInRedis(RedisConnection redisConnection) {
                    String res = ((Jedis) redisConnection.getNativeConnection()).getSet(key, value);
                    ((Jedis) redisConnection.getNativeConnection()).expire(key,loseTime);//设置失效时间
                    logger.debug("从redis里 set, key={},result={}", key, value, res);
                    return res;
                }
            });
        } catch (Exception e) {
            logger.error("redis set 异常。错误：{}", e.getMessage());
            throw new AdminRuntimeException(Module.COMMON, BaseErrorCode.REDIS_EXCEPTION);
        }

        return result;
    }


    /**
     * 原子操作 getAndSet
     * @param key
     * @param value
     * @return
     */
    public String getAndSet(final String key,final String value) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection redisConnection)
                    throws DataAccessException {
               String result =  ((Jedis) redisConnection.getNativeConnection()).getSet(key,value);
                if (result != null) {
                    return new String(result);
                }
                return null;
            }
        });
    }

    /**
     * 删除一个key
     * @param key
     * @return
     * @throws AdminRuntimeException
     */
    public Boolean del(final String key) throws AdminRuntimeException {

        if (StringUtils.isBlank(key)) {
            logger.error("del,key={},field={},value={}", key);
            throw new AdminRuntimeException(Module.COMMON, BaseErrorCode.PARAMETER_EXCEPTION, "del 参数为空");
        }

        try {
            return redisTemplate.execute(new RedisCallback<Boolean>() {

                @Override
                public Boolean doInRedis(RedisConnection redisConnection) {
                    Long res = ((Jedis) redisConnection.getNativeConnection()).del(key);
                    logger.debug("从redis里 del, key={},result={}", key, res);
                    return 1 == res;
                }
            });
        } catch (Exception e) {
            logger.error("redis del 异常。错误：{}", e.getMessage());
            throw new AdminRuntimeException(Module.COMMON, BaseErrorCode.REDIS_EXCEPTION);
        }

    }

    public Long del(final List<String> keys) throws AdminRuntimeException {

        if (CollectionUtils.isEmpty(keys)) {
            logger.error("del,key={},field={},value={}", keys);
            throw new AdminRuntimeException(Module.COMMON, BaseErrorCode.PARAMETER_EXCEPTION, "del参数为空");
        }

        try {
            return redisTemplate.execute(new RedisCallback<Long>() {

                @Override
                public Long doInRedis(RedisConnection redisConnection) {
                    String[] keysArray = keys.toArray(new String[keys.size()]);
                    Long res = ((Jedis) redisConnection.getNativeConnection()).del(keysArray);
                    logger.debug("从redis里 del, key={},result={}", keys, res);
                    return res;
                }
            });
        } catch (Exception e) {
            logger.error("redis del 异常。错误：{}", e.getMessage());
            throw new AdminRuntimeException(Module.COMMON, BaseErrorCode.REDIS_EXCEPTION);
        }
    }


    /**
     * 设置一个key 对应的值 如果这个key已经存在，返回false 如果不存在 返回true
     * @param key
     * @param value
     * @author zhangyue
     * @return
     */
    public boolean setIfNotExists(final String key,final String value) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
            logger.error("setIfNotExists入参为空,key={},value={}", key,value);
            throw new IllegalArgumentException("setIfNotExists入参为空,key="+key+",value="+value);
        }

        try {
            return redisTemplate.execute(new RedisCallback<Boolean>() {

                @Override
                public Boolean doInRedis(RedisConnection redisConnection) {
                    Long res = ((Jedis) redisConnection.getNativeConnection()).setnx(key, value);
                    logger.debug("redis setnx, key={},value={},result={}", key,value,res);
                    return 1 == res;
                }
            });
        } catch (Exception e) {
            logger.error("redis del 异常。错误：{}", e.getMessage());
        }
        return false;
    }

    /**
     * of1610 chenyong
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> zrange(final String key, final long start, final long end) {
        if (StringUtils.isBlank(key)) {
            logger.error("zrange入参为空,key={},start={},end={}", key, start, end);
            throw new IllegalArgumentException("zrange入参为空,key=" + key + ",start=" + start
                    + ",end=" + end + ".");
        }
        try {
            return redisTemplate.execute(new RedisCallback<List<String>>() {
                @Override
                public List<String> doInRedis(RedisConnection redisConnection)
                        throws DataAccessException {
                    return new ArrayList<String>(((Jedis) redisConnection.getNativeConnection())
                            .zrange(key, start, end));
                }
            });
        } catch (Exception e) {
            logger.error("redis zrange 异常。错误：{}", e.getMessage());
        }
        return new ArrayList<String>();
    }

    public List<String> zrevrange(final String key, final long start, final long end) {
        if (StringUtils.isBlank(key)) {
            logger.error("zrange入参为空,key={},start={},end={}", key, start, end);
            throw new IllegalArgumentException("zrange入参为空,key=" + key + ",start=" + start
                    + ",end=" + end + ".");
        }
        try {
            return redisTemplate.execute(new RedisCallback<List<String>>() {
                @Override
                public List<String> doInRedis(RedisConnection redisConnection)
                        throws DataAccessException {
                    return new ArrayList<String>(((Jedis) redisConnection.getNativeConnection())
                            .zrevrange(key, start, end));
                }
            });
        } catch (Exception e) {
            logger.error("redis zrange 异常。错误：{}", e.getMessage());
        }
        return new ArrayList<String>();
    }

}