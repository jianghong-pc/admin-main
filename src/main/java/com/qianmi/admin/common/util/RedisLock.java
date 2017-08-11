package com.qianmi.admin.common.util;

import com.qianmi.admin.common.exception.AdminRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * <p>redis实现分布式锁
 * 如：RedisLock lock = new RedisLock();
 * lock.lock()
 * try {
 * //do something
 * } catch(Exception e) {
 * //progress exception
 * lock.unlock();//解锁
 * }
 * </p>
 * <p>缺点：单节点redis服务器依赖过大，一旦挂了将无法获取锁</p>
 *
 */
public class RedisLock {

    private static final Logger logger = LoggerFactory.getLogger(RedisLock.class);

    private static final String KEY_PREFFIX = "redislock.";

    private RedisUtil redisClient;
    private String lockKey;
    private long timeoutmills;


    public RedisLock(RedisUtil redisClient, String lockKey, long timeoutmills) {
        this.redisClient = redisClient;
        this.lockKey = KEY_PREFFIX + lockKey;
        this.timeoutmills = timeoutmills;
    }

    private boolean acquirelock() {
        try {
            String locktime = String.valueOf(System.currentTimeMillis() + timeoutmills + 1);
            if (redisClient.setIfNotExists(lockKey, locktime)) {
                return true;
            }
            if (System.currentTimeMillis() > Long.valueOf(redisClient.get(lockKey))
                    && System.currentTimeMillis() > Long.valueOf(redisClient.getAndSet(lockKey,
                    locktime))) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("redis lock error : key={},e={}",lockKey,e.getMessage());
        }
        return false;
    }

    public boolean tryLock(){
        return acquirelock();
    }

    public boolean tryLock(long time, TimeUnit timeUnit){
        long lasttime = System.currentTimeMillis();
        long timeout = timeUnit.toMillis(time);
        while (!acquirelock() && (timeout > 0)){
            if(timeout <= 0){
                return false;
            }
            long now = System.currentTimeMillis();
            timeout -= now - lasttime;
            lasttime = now;
            try{
                Thread.sleep(timeout-1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }
        return true;
    }

    public boolean isLock(){
        try {
            //获取值
            String last = redisClient.get(lockKey);
            long lastValue = 0;
            if (StringUtils.isNotBlank(last)) {
                lastValue = Long.valueOf(last);
                long current = System.currentTimeMillis();
                //是否超时
                if (current > lastValue) {
                    return false;
                }
            } else {
                return false;
            }
        } catch (AdminRuntimeException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean unlock(){
        try {
            redisClient.del(lockKey);
            return true;
        } catch (AdminRuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }

}
