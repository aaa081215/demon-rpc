package com.demon.demonrpc.handler.domain;

/**
 * Created by wangpengpeng on 2018/12/11.
 */
public class ServerRequest {
    private Long id;
    private Object content;

    //存放某类某方法
    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
