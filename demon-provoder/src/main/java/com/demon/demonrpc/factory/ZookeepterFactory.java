package com.demon.demonrpc.factory;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created by wangpengpeng on 2018/12/10.
 */
public class ZookeepterFactory {

    public static CuratorFramework client;

    public static CuratorFramework create() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("106.14.13.61:2181", retryPolicy);
        client.start();
        return client;
    }

}
