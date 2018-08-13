package cn.com.bluemoon.zookeeper.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by leonwong on 2017/4/6.
 */
@Configuration
@EnableConfigurationProperties(ZookeeperProperties.class)
public class CuratorAutoConfiguration {

    @Bean
    @Autowired
    public CuratorFramework initCurator(ZookeeperProperties zookeeperProperties) {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(
                zookeeperProperties.getAddrs(),
                zookeeperProperties.getSessionTimeoutMs(),
                zookeeperProperties.getConnectionTimeoutMs(),
                new ExponentialBackoffRetry(1000, 3));
        curatorFramework.start();
        return curatorFramework;
    }
}
