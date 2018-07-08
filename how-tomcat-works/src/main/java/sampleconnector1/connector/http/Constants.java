package sampleconnector1.connector.http;

public class Constants {

    public static final String WEB_ROOT = sampleServletContainer1.Constants.class.getClassLoader().getResource("WEB_ROOT").getPath();

    public static final String SERVLET_ROOT = sampleServletContainer1.Constants.class.getClassLoader().getResource("servlet").getPath();

    public static final String SERVLET_PACKAGE = "servlet.";

    public static final String Package = "sampleconnector1.connector.http";
    public static final int DEFAULT_CONNECTION_TIMEOUT = 60000;
    public static final int PROCESSOR_IDLE = 0;
    public static final int PROCESSOR_ACTIVE = 1;

}
