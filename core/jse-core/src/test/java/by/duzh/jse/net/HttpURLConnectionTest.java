package by.duzh.jse.net;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.net.HttpURLConnection.HTTP_OK;

public class HttpURLConnectionTest {
    private static final String TEST_URL = "http://www.google.com/doodles";

    private HttpURLConnection connection;

    @Before
    public void init() throws Exception {
        connection = (HttpURLConnection) new URL(TEST_URL).openConnection();
    }

    @Test
    public void testGet() throws IOException {
        connection.setRequestMethod("GET");

        boolean followRedirects = HttpURLConnection.getFollowRedirects();
        Assert.assertTrue(followRedirects);

        String requestMethod = connection.getRequestMethod();
        Assert.assertEquals("GET", requestMethod);

        int responseCode = connection.getResponseCode();
        Assert.assertEquals(HTTP_OK, responseCode);

        String responseMessage = connection.getResponseMessage();
        Assert.assertEquals("OK", responseMessage);
    }
}