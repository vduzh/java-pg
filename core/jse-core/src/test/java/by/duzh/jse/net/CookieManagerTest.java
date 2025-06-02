package by.duzh.jse.net;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class CookieManagerTest {
    @Test
    @EnabledIfSystemProperty(named = "test.network", matches = "true")
    public void testCreate() throws Exception {
        CookieManager manager = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
        CookieStore store = manager.getCookieStore();
        URL url = new URL("http://www.google.com");
        Map<String, List<String>> headers = Map.of("Set-Cookie", List.of("test=value"));
        manager.put(url.toURI(), headers);
    }
}
