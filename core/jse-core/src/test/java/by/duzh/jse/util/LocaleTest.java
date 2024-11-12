package by.duzh.jse.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;
import java.util.SimpleTimeZone;

//TODO: Write tests
public class LocaleTest {
    private Locale locale;

    @Before
    public void init() {
        Locale.setDefault(new Locale("ru", "RU"));
        locale = Locale.getDefault();
    }

    @Test
    public void testCreate() {
        locale = new Locale("ru");
        locale = new Locale("ru", "RU");
    }

    @Test
    public void testGetDefault() {
        locale = Locale.getDefault();
    }

    @Test
    public void testSetDefault() {
        Locale.setDefault(Locale.US);
    }

    @Test
    public void testGetDisplayCountry() {
        Assert.assertEquals("Россия", locale.getDisplayCountry());
    }

    @Test
    public void testGetDisplayLanguage() {
        Assert.assertEquals("русский", locale.getDisplayLanguage());
    }

    @Test
    public void testGetDisplayName() {
        Assert.assertEquals("русский (Россия)", locale.getDisplayName());
    }

    @Test
    public void testGetScript() {
        String s = locale.getScript();
    }

    @Test
    public void testLocaleBuilder() {
        // TODO: Write tests for Locale.Builder
        throw new RuntimeException();
    }
}
