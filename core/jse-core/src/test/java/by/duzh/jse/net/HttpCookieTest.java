package by.duzh.jse.net;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import java.net.HttpCookie;
import java.net.URL;
import java.util.List;

public class HttpCookieTest {
    @Test
    @EnabledIfSystemProperty(named = "test.network", matches = "true")
    public void testCreate() throws Exception {
        URL url = new URL("http://www.google.com");
        HttpCookie cookie = new HttpCookie("test", "value");
        cookie.setDomain("google.com");
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        List<HttpCookie> cookies = HttpCookie.parse("test=value; Domain=google.com; Path=/; Secure; HttpOnly");
    }
}
