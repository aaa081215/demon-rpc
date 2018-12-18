package com.demon.demonrpc.handler;

import com.alibaba.fastjson.JSONObject;
import com.demon.demonrpc.handler.domain.ClientResponse;
import com.demon.demonrpc.handler.domain.DefaultFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by wangpengpeng on 2018/12/10.
 */
public class TcpClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if ("ping".equals(msg.toString())) {
            ctx.channel().writeAndFlush("ping\r\n");
            return;
        }
        ClientResponse response = JSONObject.parseObject(msg.toString(), ClientResponse.class);
        DefaultFuture.receive(response);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    }
}
