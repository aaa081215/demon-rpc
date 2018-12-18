package com.demon.demonrpc.handler.domain;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 客户端请求内容
 * Created by wangpengpeng on 2018/12/11.
 */
public class ClientRequest {
    private final long id;
    private final AtomicLong aid = new AtomicLong(1);
    private Object content;
    //存放某类某方法
    private String command;

    public ClientRequest() {
        id = aid.incrementAndGet();
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }
}
