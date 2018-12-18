package com.demon.demonrpc.handler;


import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by wangpengpeng on 2018/12/10.
 */
public class SimpleServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("得到客户端的信息：" + msg);
        ctx.channel().writeAndFlush(JSONObject.toJSON(msg) + "\r\n");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state().equals(IdleState.READER_IDLE)) {
                System.out.println("读空闲");
                ctx.channel().close();
            } else if (idleStateEvent.state().equals(IdleState.WRITER_IDLE)) {
                System.out.println("写空闲");
            } else if (idleStateEvent.state().equals(IdleState.ALL_IDLE)) {
                System.out.println("读写空闲");
                ctx.channel().writeAndFlush("ping/r/n");
            }
        }
    }

}