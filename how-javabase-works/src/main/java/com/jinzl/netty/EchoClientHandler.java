package com.jinzl.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    // 客户端连接服务器后被调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接服务器, 开始发送数据......");
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuf firstMessage = Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);
        ctx.writeAndFlush(firstMessage);
    }

    // 从服务器接收到数据后调用
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf msg) throws Exception {
        System.out.println("client 读取server数据......");
        // 服务器返回消息后
        byte[] resp = new byte[msg.readableBytes()];
        msg.readBytes(resp);
        String body = new String(resp, "UTF-8");
        System.out.println("服务端数据为: " + body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("client exceptioncaught");
        ctx.close();
    }
}
