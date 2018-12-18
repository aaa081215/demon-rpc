package com.demon.demonrpc.handler.domain;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义Future
 * Created by wangpengpeng on 2018/12/11.
 */
public class DefaultFuture {

    public final static ConcurrentHashMap<Long, DefaultFuture> allDefaultFuture = new ConcurrentHashMap<Long, DefaultFuture>();
    final Lock lock = new ReentrantLock();
    public Condition condition = lock.newCondition();
    private ClientResponse clientResponse;

    public DefaultFuture(ClientRequest request) {
        allDefaultFuture.put(request.getId(), this);
    }

    public static void receive(ClientResponse response) {

        DefaultFuture defaultFuture = allDefaultFuture.get(response.getId());
        if (defaultFuture != null) {
            Lock lock = defaultFuture.lock;
            lock.lock();
            try {
                defaultFuture.setClientResponse(response);
                defaultFuture.condition.signal();  //唤醒
                allDefaultFuture.remove(defaultFuture);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    //主线程获取数据，首先要等待结果
    public ClientResponse get() {
        lock.lock();
        try {
            while (!done()) {
                condition.await();
            }
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
        return this.clientResponse;
    }

    public ClientResponse getClientResponse() {
        return clientResponse;
    }

    public void setClientResponse(ClientResponse clientResponse) {
        this.clientResponse = clientResponse;
    }


    public boolean done() {
        if (this.clientResponse != null) {
            return true;
        } else {
            return false;
        }
    }

}
