package sampleconnector1.startup;

import sampleconnector1.connector.http.HttpConnector;

/**
 * FUNCTION:
 * author: jinzili
 * date: 2018/6/28
 */
public class Bootstrap {

    public static void main(String[] args) {
        HttpConnector connector = new HttpConnector();
        connector.start();
    }

}
