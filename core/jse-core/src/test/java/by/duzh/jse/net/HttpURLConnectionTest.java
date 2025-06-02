package by.duzh.jse.net;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.net.HttpURLConnection.HTTP_OK;

public class HttpURLConnectionTest {
    private static final String TEST_URL = "http://www.google.com/doodles";

    @Test
    @EnabledIfSystemProperty(named = "test.network", matches = "true")
    public void testGet() throws IOException {
        URL url = new URL("http://www.google.com");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        boolean followRedirects = HttpURLConnection.getFollowRedirects();
        Assertions.assertTrue(followRedirects);

        String requestMethod = connection.getRequestMethod();
        Assertions.assertEquals("GET", requestMethod);

        int responseCode = connection.getResponseCode();
        Assertions.assertEquals(200, responseCode);

        String responseMessage = connection.getResponseMessage();
        Assertions.assertEquals("OK", responseMessage);
    }
}