package sampleserver;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    public static final String WEB_ROOT = HttpServer.class.getClassLoader().getResource("WEB_ROOT").getPath();

    private static final String SHUTDOWN_METHOD = "/SHUTDOWN";

    private boolean shutdown = false;

    public void await(){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080, 1, InetAddress.getByName("127.0.0.1"));
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }

        // Loop waiting for a request
        while(!shutdown){
            Socket socket = null;
            InputStream input = null;
            OutputStream output = null;
            try {
                System.out.println("waiting for a request");
                socket = serverSocket.accept();

                input = socket.getInputStream();
                output = socket.getOutputStream();
                // create Request object and parse
                Request request = new Request(input);
                request.parse();
                // create Resposne object
                Response response = new Response(output);
                response.setRequest(request);
                response.sendStaticResource();
                // close the socket
                socket.close();
                // check if the previous URI is a shutdown command
                shutdown = StringUtils.equals(SHUTDOWN_METHOD, request.getUri());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer();
        httpServer.await();
    }

}
