package sampleconnector1;

import sampleconnector1.connector.http.*;

import javax.servlet.Servlet;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

public class ServletProcessor {

    public void process(HttpRequest request, HttpResponse response){
        String uri = request.getRequestURI();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);
        URLClassLoader loader = null;
        try {
            // create a URLClassLoader
            URL[] urls = new URL[1];
            URLStreamHandler streamHandler = null;
            File classPath = new File(Constants.SERVLET_ROOT);
            String repository = String.valueOf(new URL("file", null, classPath.getCanonicalPath() + File.separator));
            urls[0] = new URL(null, repository, streamHandler);
            loader = new URLClassLoader(urls);
        }catch (IOException e){
            e.printStackTrace();
        }

        Class myClass = null;
        try {
            myClass = loader.loadClass(Constants.SERVLET_PACKAGE + servletName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Servlet servlet = null;

        try {
            servlet = (Servlet) myClass.newInstance();
            HttpRequestFacade requestFacade = new HttpRequestFacade(request);
            HttpResponseFacade responseFacade = new HttpResponseFacade(response);
            servlet.service(requestFacade, responseFacade);
            response.finishResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
