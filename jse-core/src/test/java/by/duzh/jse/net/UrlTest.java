package by.duzh.jse.net;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class UrlTest {
    private URL url;

    @Before
    public void init() throws UnknownHostException {
    }

    @Test
    public void testCreate() throws MalformedURLException {
        String spec = "http://www.google.com:80/doodles";
        url = new URL(spec);

        Assert.assertEquals("http", url.getProtocol());
        Assert.assertEquals("www.google.com", url.getHost());
        Assert.assertEquals(80, url.getPort());
        Assert.assertEquals("/doodles", url.getFile());
        Assert.assertEquals(spec, url.toExternalForm());

        spec = "http://www.google.com/doodles";
        Assert.assertEquals(80, url.getPort());

        url = new URL("http", "www.google.com", "80");
        url = new URL("http", "www.google.com", 80, "doodles");
    }

    @Test
    public void testOpenConnection() throws MalformedURLException, IOException {
        url = new URL("http://www.google.com:80/doodles");

        URLConnection connection = url.openConnection();
        // get headers and etc.
    }
}
