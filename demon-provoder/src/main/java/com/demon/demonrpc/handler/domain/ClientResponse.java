package com.demon.demonrpc.handler.domain;

/**
 * 响应结果
 * Created by wangpengpeng on 2018/12/11.
 */
public class ClientResponse {
    private long id;
    private Object result;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
