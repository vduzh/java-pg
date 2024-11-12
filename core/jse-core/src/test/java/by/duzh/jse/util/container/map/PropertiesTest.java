package by.duzh.jse.util.container.map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.*;

// Old class
// Note: extends Hashtable with <String, String>
public class PropertiesTest {
    private Properties properties;

    @Before
    public void setUp() {
        properties = new Properties();
    }

    @Test
    public void testJDK10Create() {
        properties = new Properties(3);
        Assert.assertEquals(0, properties.size());
    }

    @Test
    public void testSetProperty() {
        properties.setProperty("1", "One");
        Assert.assertEquals(1, properties.size());
    }

    @Test
    public void testGetProperty() {
        properties.setProperty("1", "One");

        Assert.assertNull(properties.getProperty("foo"));
        Assert.assertEquals("bar", properties.getProperty("foo", "bar"));
        Assert.assertEquals("one", properties.getProperty("1"));
    }

    @Test
    public void testJDK10Replace() {
        properties.put("1", "one");
        properties.put("2", "two");

        Assert.assertEquals("two", properties.replace("2", "bar"));
        Assert.assertNull(properties.replace("3", "baz"));

        Assert.assertTrue(properties.replace("2", "bar", "foo"));
        Assert.assertFalse(properties.replace("2", "foo1", "ex"));
    }

    @Test
    public void testListToPrintStream() {
        properties.put("1", "one");
        properties.put("2", "two");

        PrintStream ps = System.out;

        properties.list(ps);
    }

    @Test
    public void testListToPrintWriter() {
        properties.put("1", "one");
        properties.put("2", "two");

        PrintWriter writer = new PrintWriter(System.out, true);

        properties.list(writer);
    }

    @Test
    public void testLoadFromIS() throws IOException {
        String s = "1=one\n2=two";
        InputStream is = new ByteArrayInputStream(s.getBytes());

        properties.load(is);

        Assert.assertEquals(2, properties.size());
        Assert.assertEquals("one", properties.getProperty("1"));
        Assert.assertEquals("two", properties.getProperty("2"));
    }

    @Test
    public void testLoadFromReader() throws IOException {
        String s = "1=one\n2=two";
        InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(s.getBytes()));

        properties.load(reader);

        Assert.assertEquals(2, properties.size());
        Assert.assertEquals("one", properties.getProperty("1"));
        Assert.assertEquals("two", properties.getProperty("2"));
    }

    @Test
    public void testLoadFromXML() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n")
                .append("<!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\">\n")
                .append("<properties>\n")
                .append("<entry key=\"1\">one</entry>\n")
                .append("<entry key=\"2\">two</entry>\n")
                .append("</properties>\n");

        properties.loadFromXML(new ByteArrayInputStream(sb.toString().getBytes()));

        Assert.assertEquals(2, properties.size());
        Assert.assertEquals("one", properties.getProperty("1"));
        Assert.assertEquals("two", properties.getProperty("2"));
    }

    @Test
    public void testPropertyNames() {
        properties.setProperty("1", "One");
        properties.setProperty("2", "Two");

        Enumeration<?> names = properties.propertyNames();
    }

    @Test
    public void testStore() throws IOException {
        properties.setProperty("1", "One");
        properties.setProperty("2", "Two");

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        properties.store(os, "it is comment");
        os.toString();
    }

    @Test
    public void testStringPropertyNames() {
        properties.setProperty("1", "One");
        properties.setProperty("2", "Two");

        Set<String> names = properties.stringPropertyNames();

        Assert.assertTrue(names.containsAll(Arrays.asList("1", "2")));
    }
}
