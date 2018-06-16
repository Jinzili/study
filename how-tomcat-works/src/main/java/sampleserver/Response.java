package sampleserver;

import lombok.Data;

import java.io.*;

@Data
public class Response {

    private static final int BUFFER_SIZE = 1024;

    private Request request;

    private OutputStream output;

    public Response(OutputStream output){
        this.output = output;
    }

    public void setRequest(Request request){
        this.request = request;
    }

    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            File file = new File(HttpServer.WEB_ROOT + request.getUri());

            if(file.exists()){
                String responseHead = "HTTP/1.1 200 OK \r\n" +
                        "Server: Apache-Coyote/1.1\r\n" +
                        "Content-Type: text/html\r\n" +
                        "\r\n";
                output.write(responseHead.getBytes());
                fis = new FileInputStream(file);
                int ch = fis.read(bytes, 0, BUFFER_SIZE);
                while(ch != -1) {
                    System.out.println(new String(bytes));
                    output.write(bytes, 0, ch);
                    ch = fis.read(bytes, 0, BUFFER_SIZE);
                }
            }else{
                String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: 23\r\n" +
                        "\r\n" +
                        "<h1>File Not Found</h1>";
                output.write(errorMessage.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        } finally {
            if(fis != null){
                fis.close();
            }
        }
    }

}