package cn.com.bluemoon.lock.lockUtil;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import cn.com.bluemoon.lock.exception.LockException;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by leonwong on 2016/12/20.
 */
public class DistributedExclusiveCuratorLock implements Lock, Serializable {

    private static final long serialVersionUID = -2646307661923742319L;

    private String rootPath;

    private InterProcessLock interProcessLock;


    public DistributedExclusiveCuratorLock(CuratorFramework curatorFramework, String lockKey, String rootPath) {
        this.rootPath = rootPath;
        interProcessLock = new InterProcessMutex(curatorFramework, this.rootPath + "/" + lockKey);
    }

    public DistributedExclusiveCuratorLock(CuratorFramework curatorFramework, String lockKey) {
        this(curatorFramework, lockKey, "/locks");
    }

    @Override
    public void lock() {
        if (!this.tryLock())
            throw new LockException("获取锁出现故障");
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        boolean isSucceed = true;
        try {
            interProcessLock.acquire();
        } catch (Exception e) {
            e.printStackTrace();
            isSucceed = false;
        }
        return isSucceed;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        boolean isSucceed = false;
        try {
            isSucceed = interProcessLock.acquire(time, unit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSucceed;
    }

    @Override
    public void unlock() {
        try {
            interProcessLock.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    public DistributedExclusiveCuratorLock setRootPath(String rootPath) {
        this.rootPath = rootPath;
        return this;
    }
}
