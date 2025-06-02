package by.duzh.jse.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.logging.Logger;

import java.util.Locale;
import java.util.SimpleTimeZone;

//TODO: Write tests
public class LocaleTest {
    private static final Logger logger = Logger.getLogger(LocaleTest.class.getName());

    private Locale locale;

    @BeforeEach
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
        Assertions.assertEquals("Россия", locale.getDisplayCountry());
    }

    @Test
    public void testGetDisplayLanguage() {
        Assertions.assertEquals("русский", locale.getDisplayLanguage());
    }

    @Test
    public void testGetDisplayName() {
        Assertions.assertEquals("русский (Россия)", locale.getDisplayName());
    }

    @Test
    public void testGetScript() {
        String s = locale.getScript();
    }

    @Test
    public void testLocaleBuilder() {
        // TODO: Write tests for Locale.Builder
        logger.warning("WARNING!!! Test is not implemented yet!");
    }
}
