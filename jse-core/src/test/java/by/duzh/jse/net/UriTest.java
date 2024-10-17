package by.duzh.jse.net;

import org.junit.Before;
import org.junit.Test;

import java.net.CookieHandler;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public class UriTest {
    private URI uri;

    @Before
    public void init() throws UnknownHostException {
    }

    @Test
    public void testCreate() throws URISyntaxException {
        //TODO: implement...
        throw new RuntimeException("UriTest is not implemented");
/*
        String spec = "http://www.google.com:80/doodles";
        uri = new URI(spec);

        Assert.assertEquals("www.google.com", uri.getHost());
        Assert.assertEquals(80, uri.getPort());

        spec = "http://www.google.com/doodles";
        Assert.assertEquals(80, uri.getPort());

*/
   }
}
