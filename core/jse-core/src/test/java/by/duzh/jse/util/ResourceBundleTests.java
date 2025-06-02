package by.duzh.jse.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class ResourceBundleTests {

    private ResourceBundle bundle;

    @BeforeEach
    public void init() {
        bundle = ResourceBundle.getBundle("sample");
    }

    @Test
    public void testGetBaseBundleName() {
        Assertions.assertEquals("sample", bundle.getBaseBundleName());
    }

    @Test
    public void testGetKeys() {
        String[] values = {"foo", "user"};

        Enumeration<String> keys = bundle.getKeys();
        while(keys.hasMoreElements()) {
            Assertions.assertTrue(Arrays.stream(values).anyMatch(keys.nextElement()::equals));
        }
    }

    @Test
    public void testContainsKey() {
        Assertions.assertTrue(bundle.containsKey("user"));
        Assertions.assertTrue(bundle.containsKey("foo"));
    }

    @Test
    public void testGetString() {
        Assertions.assertEquals("admin", bundle.getString("user"));
    }

    @Test
    public void testGetLocale() {
        Assertions.assertEquals("", bundle.getLocale().getLanguage());
        Assertions.assertEquals("", bundle.getLocale().getCountry());
    }
}
