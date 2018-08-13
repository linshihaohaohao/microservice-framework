package cn.com.bluemoon.lock.lockUtil;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import cn.com.bluemoon.lock.exception.IllegalReentrantException;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 不可重入分布式锁,基于redis实现
 * <p>
 * Created by LeonWong on 16/8/26.
 */
public class DistributedExclusiveRedisLock implements Lock, Serializable {
    private static final long serialVersionUID = -7118885188373628439L;

    private RedisTemplate redisTemplate;

    /**
     * 控制锁颗粒度的参数
     * <p>
     * 不建议使用全局锁,具体应用中推荐指定对应的Key,把锁的颗粒度减小,利于性能
     */
    private String lockKey = "distributed_global_lock";

    private String uuid;

    private boolean isOccupy;

    // 单位 默认10秒
    private long expires = 30L;

    public DistributedExclusiveRedisLock(RedisTemplate template) {
        this.redisTemplate = template;
    }

    public DistributedExclusiveRedisLock(RedisTemplate template, String lockKey) {
        this.lockKey = lockKey;
        this.redisTemplate = template;
    }

    /**
     * 获取锁方法,获取不到会被阻塞
     */
    @Override
    public void lock() {
        if (isOccupy) {
            throw new IllegalReentrantException("锁不可重入,请检查代码");
        }
        isOccupy = true;
        uuid = UUID.randomUUID().toString();
        boolean isAcquired;
        for (; ; ) {
            isAcquired = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.setNX(lockKey.getBytes(), uuid.getBytes())
                            && connection.expire(lockKey.getBytes(), expires);
                }
            });
            if (isAcquired)
                return;
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    /**
     * 尝试获取锁,支持超时中断 (暂未实现)
     *
     * @param time
     * @param unit
     * @return
     * @throws InterruptedException
     */
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    /**
     * 解锁并释放资源
     * <p>
     * 超时后的资源被释放掉,避免误删,这里务必校验uuid
     */
    @Override
    public void unlock() {
        if (!isOccupy)
            return;
        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] lockBytes = connection.get(lockKey.getBytes());
                if (lockBytes != null && new String(lockBytes).equals(uuid)) {
                    connection.del(lockKey.getBytes());
                }
                return true;
            }
        });
        isOccupy = false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public String getLockKey() {
        return lockKey;
    }

    public void setLockKey(String lockKey) {
        this.lockKey = lockKey;
    }
}
