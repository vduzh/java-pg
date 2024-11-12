package by.duzh.jse.net;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.*;

public class URLConnectionTest {
    private static final String TEST_URL = "http://www.google.com/doodles";

    private URLConnection connection;

    @Before
    public void init() throws Exception {
        connection = new URL(TEST_URL).openConnection();
    }

    @Test
    public void testGet() {
        int length = connection.getContentLength();
        Assert.assertTrue(length != -1);

        long lengthLong = connection.getContentLengthLong();
        Assert.assertTrue(lengthLong != -1);

        String contentType = connection.getContentType();
        Assert.assertEquals("text/html; charset=utf-8", contentType);

        long dateInMils = connection.getDate();
        Assert.assertTrue(dateInMils > 0);

        long expDateInMils = connection.getExpiration();
        Assert.assertTrue(expDateInMils > 0);
/*
        Formatter formatter = new Formatter().format("%tD", new Date(expDateInMils));
        System.out.println(formatter.toString());
        formatter.close();
*/
        long lastModifiedInMils = connection.getLastModified();
        //Assert.assertTrue(lastModifiedInMils > 0);

        connection.getHeaderFields().entrySet().forEach(System.out::println);

        String header = connection.getHeaderField(0);
        //System.out.println(header);

        header = connection.getHeaderField("Content-Type");
        //System.out.println(header);

        header = connection.getHeaderFieldKey(1);
        //System.out.println(header);
    }
}
