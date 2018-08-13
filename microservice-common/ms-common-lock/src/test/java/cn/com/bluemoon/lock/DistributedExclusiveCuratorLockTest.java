//package cn.com.bluemoon.lock;
//
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.CuratorFrameworkFactory;
//import org.apache.curator.retry.ExponentialBackoffRetry;
//import org.junit.Test;
//
//import cn.com.bluemoon.lock.lockUtil.DistributedExclusiveCuratorLock;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
///**
// * Created by leonwong on 2016/12/21.
// */
//public class DistributedExclusiveCuratorLockTest {
//    private ExecutorService executorService = Executors.newFixedThreadPool(10);
//
//    @Test
//    public void doRunning() {
//        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("127.0.0.1:2181", 10000, 10000, new ExponentialBackoffRetry(1000, 3));
//        curatorFramework.start();
//
//        for (int i = 1; i < 10; i++) {
//            executorService.execute(new TestCuratorTask(curatorFramework));
//        }
//        try {
//            Thread.sleep(10000000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//class TestCuratorTask implements Runnable {
//
//    private CuratorFramework curatorFramework;
//
//    public TestCuratorTask(CuratorFramework curatorFramework) {
//        this.curatorFramework = curatorFramework;
//    }
//
//    @Override
//    public void run() {
//        DistributedExclusiveCuratorLock lock = new DistributedExclusiveCuratorLock(curatorFramework, "resources", "/curator_locks");
//
//        try {
//            System.out.println("正在获取锁===" + Thread.currentThread().getName());
//            lock.lock();
//            System.out.println(Thread.currentThread().getName() + "拿到锁啦!!!!");
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        } finally {
//            lock.unlock();
//            System.out.println(Thread.currentThread().getName() + "释放锁啦!!!");
//        }
//    }
//}
