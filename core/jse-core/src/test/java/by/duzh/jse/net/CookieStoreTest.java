package by.duzh.jse.net;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URL;
import java.util.List;

public class CookieStoreTest {
    @Test
    @EnabledIfSystemProperty(named = "test.network", matches = "true")
    public void testCreate() throws Exception {
        CookieManager manager = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
        CookieStore store = manager.getCookieStore();
        URL url = new URL("http://www.google.com");
        HttpCookie cookie = new HttpCookie("test", "value");
        cookie.setDomain("google.com");
        cookie.setPath("/");
        store.add(url.toURI(), cookie);
        List<HttpCookie> cookies = store.get(url.toURI());
    }
}
