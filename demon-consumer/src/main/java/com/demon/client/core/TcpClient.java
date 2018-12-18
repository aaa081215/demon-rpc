package com.demon.client.core;

import com.alibaba.fastjson.JSONObject;
import com.demon.client.handler.TcpClientHandler;
import com.demon.client.parame.ClientRequest;
import com.demon.client.parame.Response;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 长连接异步获取获取响应结果
 * Created by wangpengpeng on 2018/12/10.
 */
public class TcpClient {

    static final String host = "localhost";
    static final int port = 8888;
    static final Bootstrap b = new Bootstrap();
    static ChannelFuture f = null;

    static {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        b.group(workerGroup);
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Delimiters.lineDelimiter()[0]));
                ch.pipeline().addLast(new StringDecoder());
                ch.pipeline().addLast(new TcpClientHandler());
                ch.pipeline().addLast(new StringEncoder());
            }
        });
        try {
            f = b.connect(host, port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //高并发问题
    //发送数据
    public static Response send(ClientRequest request) {
        ClientRequest request2 = request;
        f.channel().writeAndFlush(JSONObject.toJSON(request) + "\r\n");
        DefaultFuture df = new DefaultFuture(request);
        return df.get(1000 * 60 * 5);
    }

    public static void main(String[] args) {
        ClientRequest request = new ClientRequest();
        request.setContent("我要辞职le");
        Response response = TcpClient.send(request);
        System.out.println(response.getId() + "***" + response.getResult());

    }

}
