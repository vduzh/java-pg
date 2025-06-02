package by.duzh.jse.net;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class UrlTest {
    private URL url;

    @BeforeEach
    public void init() throws UnknownHostException {
    }

    @Test
    @EnabledIfSystemProperty(named = "test.network", matches = "true")
    public void testCreate() throws MalformedURLException {
        String spec = "http://www.google.com:80/doodles";
        url = new URL(spec);

        Assertions.assertEquals("http", url.getProtocol());
        Assertions.assertEquals("www.google.com", url.getHost());
        Assertions.assertEquals(80, url.getPort());
        Assertions.assertEquals("/doodles", url.getFile());
        Assertions.assertEquals(spec, url.toExternalForm());

        spec = "http://www.google.com/doodles";
        Assertions.assertEquals(80, url.getPort());

        url = new URL("http", "www.google.com", "80");
        url = new URL("http", "www.google.com", 80, "doodles");
    }

    @Test
    @EnabledIfSystemProperty(named = "test.network", matches = "true")
    public void testOpenConnection() throws MalformedURLException, IOException {
        url = new URL("http://www.google.com:80/doodles");

        URLConnection connection = url.openConnection();
        // get headers and etc.
    }
}
