//package cn.com.bluemoon.lock;
//
//import org.junit.Test;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//
//import cn.com.bluemoon.lock.lockUtil.DistributedExclusiveRedisLock;
//import redis.clients.jedis.JedisShardInfo;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
///**
// * Created by LeonWong on 16/8/26.
// */
//public class DistributedExclusiveRedisLockTest {
//
//    private ExecutorService executorService = Executors.newFixedThreadPool(10);
//
//    @Test
//    public void doRunning() {
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
//        jedisConnectionFactory.setUsePool(true);
//        JedisShardInfo shardInfo = new JedisShardInfo("127.0.0.1",6379);
//        jedisConnectionFactory.setShardInfo(shardInfo);
//        RedisTemplate redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(jedisConnectionFactory);
//        redisTemplate.afterPropertiesSet();
//
//        for (int i = 1; i < 10; i++) {
//            executorService.execute(new TestTask(redisTemplate));
//        }
//        try {
//            Thread.sleep(10000000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//class TestTask implements Runnable {
//
//    private RedisTemplate template;
//
//    public TestTask(RedisTemplate template) {
//        this.template = template;
//    }
//
//    @Override
//    public void run() {
//        DistributedExclusiveRedisLock lock = new DistributedExclusiveRedisLock(template);
//        System.out.println("正在获取锁===" + Thread.currentThread().getName());
//        lock.lock();
//        System.out.println(Thread.currentThread().getName() + "拿到锁啦!!!!");
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        lock.unlock();
//        System.out.println(Thread.currentThread().getName() + "释放锁啦!!!");
//    }
//}