package com.jinzl.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class EchoClient {

    private String host;
    private Integer port;
    private NioEventLoopGroup nioEventLoopGroup = null;

    public EchoClient(String host, Integer port){
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception{
        try {
            // 客户端引导类
            Bootstrap bootstrap = new Bootstrap();
            // EventLoopGroup可以理解为是一个线程池, 这个线程池用来处理连接、接收数据
            // 发送数据
            nioEventLoopGroup = new NioEventLoopGroup();
            bootstrap.group(nioEventLoopGroup) // 多线程处理
                    .channel(NioSocketChannel.class) // 制定通道类型为NioServerSocketChannel
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler()); // 注册handler
                        }
                    });
            // 连接服务器
            ChannelFuture channelFuture = bootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            nioEventLoopGroup.shutdownGracefully().sync();
        }

    }

    public static void main(String[] args) throws Exception {
        EchoClient echoClient = new EchoClient("localhost", 20000);
        echoClient.start();
    }

}
