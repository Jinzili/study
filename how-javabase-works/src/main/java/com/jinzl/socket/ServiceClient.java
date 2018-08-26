package com.jinzl.socket;

import java.io.*;
import java.net.Socket;

public class ServiceClient {

    public static void main(String[] args) throws IOException {
        // 和服务器发送请求建立了连接
        Socket socket = new Socket("localhost", 8080);
        // 从socket获取输入输出流
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        PrintWriter pw = new PrintWriter(os);
        pw.println("hello, server");
        pw.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String result = br.readLine();
        System.out.println(result);

        socket.close();

    }

}
