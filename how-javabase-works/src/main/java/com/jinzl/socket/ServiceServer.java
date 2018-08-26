package com.jinzl.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServiceServer {

    public static void main(String[] args) throws IOException {

        // 创建一个serverSocket 绑定到本机的8080端口上
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress("localhost", 8080));

        // 接收客户端的连接请求, accpet时一个阻塞方法, 会一直等待到有客户端连接
        while(true){
            Socket socket = server.accept();
            new Thread(new ServiceServerTask(socket)).start();
        }


    }

}
