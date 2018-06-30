package sampleServletContainer1;

/**
 * FUNCTION:
 * author: jinzili
 * date: 2018/6/26
 */
public class Constants {

    public static final String WEB_ROOT = Constants.class.getClassLoader().getResource("WEB_ROOT").getPath();

    public static final String SERVLET_ROOT = Constants.class.getClassLoader().getResource("servlet").getPath();

}
