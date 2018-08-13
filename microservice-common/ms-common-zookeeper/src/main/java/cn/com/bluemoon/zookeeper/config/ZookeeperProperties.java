package cn.com.bluemoon.zookeeper.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by leonwong on 2017/4/6.
 */
@ConfigurationProperties(prefix = "zookeeper")
public class ZookeeperProperties {
    private String address;

    private String basePath;

    private int sessionTimeoutMs;

    private int connectionTimeoutMs;

    private int gcIntervalSecond;

    private boolean hasGc;

    public String getAddrs() {
        return address;
    }

    public ZookeeperProperties setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getBasePath() {
        return basePath;
    }

    public ZookeeperProperties setBasePath(String basePath) {
        this.basePath = basePath;
        return this;
    }

    public int getSessionTimeoutMs() {
        return sessionTimeoutMs;
    }

    public ZookeeperProperties setSessionTimeoutMs(int sessionTimeoutMs) {
        this.sessionTimeoutMs = sessionTimeoutMs;
        return this;
    }

    public int getConnectionTimeoutMs() {
        return connectionTimeoutMs;
    }

    public ZookeeperProperties setConnectionTimeoutMs(int connectionTimeoutMs) {
        this.connectionTimeoutMs = connectionTimeoutMs;
        return this;
    }

    public int getGcIntervalSecond() {
        return gcIntervalSecond;
    }

    public ZookeeperProperties setGcIntervalSecond(int gcIntervalSecond) {
        this.gcIntervalSecond = gcIntervalSecond;
        return this;
    }

    public boolean isHasGc() {
        return hasGc;
    }

    public ZookeeperProperties setHasGc(boolean hasGc) {
        this.hasGc = hasGc;
        return this;
    }
}
