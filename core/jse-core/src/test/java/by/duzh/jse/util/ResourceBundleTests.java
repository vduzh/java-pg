package by.duzh.jse.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class ResourceBundleTests {

    private ResourceBundle bundle;

    @Before
    public void init() {
        bundle = ResourceBundle.getBundle("sample");
    }

    @Test
    public void testGetBaseBundleName() {
        Assert.assertEquals("sample", bundle.getBaseBundleName());
    }

    @Test
    public void testGetKeys() {
        String[] values = {"foo", "user"};

        Enumeration<String> keys = bundle.getKeys();
        while(keys.hasMoreElements()) {
            Assert.assertTrue(Arrays.stream(values).anyMatch(keys.nextElement()::equals));
        }
    }

    @Test
    public void testContainsKey() {
        Assert.assertTrue(bundle.containsKey("user"));
        Assert.assertTrue(bundle.containsKey("foo"));
    }

    @Test
    public void testGetString() {
        Assert.assertEquals("admin", bundle.getString("user"));
    }

    @Test
    public void testGetLocale() {
        Assert.assertEquals("", bundle.getLocale().getLanguage());
        Assert.assertEquals("", bundle.getLocale().getCountry());
    }
}
