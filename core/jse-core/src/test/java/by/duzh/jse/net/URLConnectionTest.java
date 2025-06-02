package by.duzh.jse.net;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class URLConnectionTest {
    @Test
    @EnabledIfSystemProperty(named = "test.network", matches = "true")
    public void testGet() throws IOException {
        URL url = new URL("http://www.google.com");
        URLConnection connection = url.openConnection();

        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        String contentType = connection.getContentType();
        Assertions.assertEquals("text/html; charset=ISO-8859-1", contentType);

        int contentLength = connection.getContentLength();
        Assertions.assertTrue(contentLength > 0);
    }
}
