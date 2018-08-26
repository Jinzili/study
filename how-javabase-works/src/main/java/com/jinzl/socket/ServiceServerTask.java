package com.jinzl.socket;

import java.io.*;
import java.net.Socket;

public class ServiceServerTask implements Runnable {

    private Socket socket;

    public ServiceServerTask(Socket socket){
        this.socket = socket;
    }

    // 业务逻辑, 跟客户端进行数据交互
    @Override
    public void run() {
        InputStream is = null;
        OutputStream os = null;
        try {
            // 从socket连接中获取到与client之间的网络通信输入输出流
            is = socket.getInputStream();
            os = socket.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            // 从网络通信输入流中读取客户端发送过来的数据
            // socketinputstream的读数据的方法都是阻塞的
            String param = br.readLine();
            GetDataServiceImpl getDataService = new GetDataServiceImpl();
            String result = getDataService.getData(param);
            // 将返回数据写道socket的输出流中, 以发送客户端
            PrintWriter pw = new PrintWriter(os);
            pw.println(result);
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(is != null){
                    is.close();
                }
                if(os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
