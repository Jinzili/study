package sampleconnector1;

import sampleconnector1.connector.http.HttpRequest;
import sampleconnector1.connector.http.HttpResponse;

public class StaticResourceProcessor {

    public void process(HttpRequest request, HttpResponse response){
        try {
            response.sendStaticResource();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
