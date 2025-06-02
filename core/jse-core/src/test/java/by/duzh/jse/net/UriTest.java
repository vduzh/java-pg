package by.duzh.jse.net;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.CookieHandler;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UriTest {
    private URI uri;

    @BeforeEach
    public void init() throws UnknownHostException {
    }

    @Test
    public void testCreate() throws URISyntaxException {
        //TODO: implement...
        System.out.println("Test not implemented: UriTest");
/*
        String spec = "http://www.google.com:80/doodles";
        uri = new URI(spec);

        assertEquals("www.google.com", uri.getHost());
        assertEquals(80, uri.getPort());

        spec = "http://www.google.com/doodles";
        assertEquals(80, uri.getPort());

*/
   }
}
