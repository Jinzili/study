package sampleconnector1.http;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * FUNCTION:
 * author: jinzili
 * date: 2018/6/28
 */
public class HttpRequest  {

    private String contentType;
    private int contentLength;
    private InetAddress inetAddress;
    private InputStream input;
    private String method;
    private String protocol;
    private String queryString;
    private String requestURI;
    private String serverName;
    private int serverPort;
    private Socket socket;
    private boolean requestedSessionCookie;
    private String requestedSessionId;
    private boolean requestedSessionURL;

    /**
     * The request attributes for this request
     */
    protected HashMap attributes = new HashMap();
    /**
     * The authorization credentials sent with this Request
     */
    protected String authorization = null;
    /**
     * The context path for this request
     */
    protected String contextPath = "";
    /**
     * The set of cookies associated with this Request
     */
    protected ArrayList cookies = new ArrayList();
    /**
     * An empty collection to use for returning empty Enumerations. Do not
     * add any elements to this collection
     */
    protected static ArrayList empty = new ArrayList();
    /**
     * The set of SimpleDateFormat formats to use in GetDateHeader()
     */
    protected SimpleDateFormat formats[] = {
        new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US),
        new SimpleDateFormat("EEEEEE, dd-MMM-yy HH:mm:ss zzz", Locale.US),
        new SimpleDateFormat("EEE MMMM d HH:mm:ss yyyy", Locale.US)
    };
    /**
     * The HTTP headers associated with this Request, keyed by name, The values
     * are ArrayLists of the corresponding header values
     */
    protected HashMap headers = new HashMap();

}
