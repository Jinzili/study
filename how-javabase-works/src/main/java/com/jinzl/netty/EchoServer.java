package com.jinzl.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {

    private final Integer port;

    public EchoServer(Integer port){
        this.port = port;
    }

    public void start() throws Exception{
        EventLoopGroup eventExecutors = null;
        try {
            // server端引导类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 连接池处理数据
            eventExecutors = new NioEventLoopGroup();
            // 装配serverBootstrap
            serverBootstrap.group(eventExecutors)
                    .channel(NioServerSocketChannel.class)
                    .localAddress("localhost", port)
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            // 可添加多个InHandler OutHandler
                            // 添加顺序为: IN-1 OUT-1 OUT-2 IN-2
                            // 执行顺序为: IN-1 IN-2 OUT-2 OUT-1
                            // WARNING: OutHandler不能放在最后, 最后只能是InHandler
                            channel.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            // 最后绑定服务器等待直到绑定完成, 调用sync方法会阻塞直到服务器完成绑定
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            System.out.println("开始监听, 端口: " + channelFuture.channel());
            channelFuture.channel().closeFuture().sync();
        }finally {
            if(eventExecutors != null){
                eventExecutors.shutdownGracefully().sync();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        EchoServer echoServer = new EchoServer(20000);
        echoServer.start();
    }

}
