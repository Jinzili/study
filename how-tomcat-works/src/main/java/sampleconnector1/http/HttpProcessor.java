package sampleconnector1.http;

import java.net.Socket;

/**
 * FUNCTION: this class used to be called HttpServer
 * author: jinzili
 * date: 2018/6/28
 */
public class HttpProcessor {

    public HttpProcessor(HttpConnector connector){
        this.connector = connector;
    }

    /**
     * The HttpConnector with which this processor is associated
     */
    private HttpConnector connector = null;
    private HttpRequest request;


    public void process(Socket socket){

    }

}
