package com.demon.client.core;

import com.demon.client.parame.ClientRequest;
import com.demon.client.parame.Response;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 异步获取服务方响应信息
 * 解决高并发场景下线程安全问题
 * 设置响应超时时间
 * 自定义Future
 * Created by wangpengpeng on 2018/12/11.
 */
public class DefaultFuture {


    public final static ConcurrentHashMap<Long, DefaultFuture> allDefaultFuture = new ConcurrentHashMap<Long, DefaultFuture>();

    //监听线程
    static {
        FutureThread futureThread = new FutureThread();
        futureThread.setDaemon(true);
        futureThread.start();
    }

    final Lock lock = new ReentrantLock();
    public Condition condition = lock.newCondition();
    private Response clientResponse;
    private long timeout = 2 * 60 * 1000;
    private long startTime = System.currentTimeMillis();

    public DefaultFuture(ClientRequest request) {
        allDefaultFuture.put(request.getId(), this);
    }

    public static void receive(Response response) {

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

    //超时时间
    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    //主线程获取数据，首先要等待结果
    public Response get(long time) {
        lock.lock();
        try {
            while (!done()) {
                condition.await(time, TimeUnit.SECONDS);
                if ((System.currentTimeMillis() - startTime) > time) {
                    System.out.println("请求超时");
                    break;
                }
            }
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
        return this.clientResponse;
    }

    public Response getClientResponse() {
        return clientResponse;
    }

    public void setClientResponse(Response clientResponse) {
        this.clientResponse = clientResponse;
    }

    public boolean done() {
        if (this.clientResponse != null) {
            return true;
        } else {
            return false;
        }
    }

    static class FutureThread extends Thread {
        @Override
        public void run() {
            Set<Long> keys = allDefaultFuture.keySet();
            for (Long id : keys) {
                DefaultFuture defaultFuture = allDefaultFuture.get(id);
                if (defaultFuture == null) {
                    allDefaultFuture.remove(id);
                } else {
                    if (defaultFuture.getTimeout() < System.currentTimeMillis() - defaultFuture.getStartTime()) {
                        Response response = new Response();
                        response.setId(id);
                        response.setCode("250");
                        response.setMsg("RpcTimeLimitOut");
                        receive(response);
                    }
                }
            }
        }
    }

}
