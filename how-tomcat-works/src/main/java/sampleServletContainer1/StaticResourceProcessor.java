package sampleServletContainer1;

import java.io.IOException;

/**
 * FUNCTION:
 * author: jinzili
 * date: 2018/6/27
 */
public class StaticResourceProcessor {

    public void process(Request request, Response response){
        try {
            response.sendStaticResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
