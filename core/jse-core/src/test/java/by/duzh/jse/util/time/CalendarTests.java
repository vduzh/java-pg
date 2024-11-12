package by.duzh.jse.util.time;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.*;

public class CalendarTests {
    private static final Date TEST_DATE = new Date(999999999999L); // Sep 09 04:46:39 MSD 2001

    private Calendar calendar;

    @Before
    public void init() {
        calendar = Calendar.getInstance();
    }

    @Test
    public void testSet() {
        calendar.set(2020, Calendar.FEBRUARY, 10);
        calendar.set(2020, Calendar.FEBRUARY, 10, 8, 15);
        calendar.set(2020, Calendar.FEBRUARY, 10, 8, 15, 30);
    }

    @Test
    public void testSetTime() {
        calendar.setTime(TEST_DATE);
    }

    @Test
    public void testGet() {
        calendar.setTime(TEST_DATE);

        //TODO: have a look how date is stored under keys!!!
        Assert.assertEquals(2001, calendar.get(Calendar.YEAR));
        Assert.assertEquals(Calendar.SEPTEMBER, calendar.get(Calendar.MONTH));
        Assert.assertEquals(9, calendar.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(4, calendar.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(46, calendar.get(Calendar.MINUTE));
        Assert.assertEquals(39, calendar.get(Calendar.SECOND));
        Assert.assertEquals(999, calendar.get(Calendar.MILLISECOND));
    }

    @Test
    public void testGetTime() {
        calendar.setTime(TEST_DATE);
        Assert.assertEquals(TEST_DATE, calendar.getTime());
    }

    @Test
    public void testIsSet() {
        calendar.setTime(TEST_DATE);
        Assert.assertTrue(calendar.isSet(Calendar.YEAR));

        calendar.clear();
        Assert.assertFalse(calendar.isSet(Calendar.YEAR));
    }

    @Test
    public void testAdd() {
        calendar.setTime(TEST_DATE);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
    }

    @Test
    public void testAfter() {
        calendar.setTime(TEST_DATE);
        Assert.assertTrue(Calendar.getInstance().after(calendar));
    }

    @Test
    public void testBefore() {
        calendar.setTime(TEST_DATE);
        Assert.assertTrue(calendar.before(Calendar.getInstance()));
    }

    @Test
    public void testClear() {
        calendar.clear();

        Assert.assertFalse(calendar.isSet(Calendar.YEAR));
        Assert.assertEquals(1970, calendar.get(Calendar.YEAR));
        Assert.assertEquals(0, calendar.get(Calendar.MONTH));
        Assert.assertEquals(1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testEquals() {
        calendar.setTime(TEST_DATE);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(TEST_DATE);

        Assert.assertEquals(calendar, calendar1);
        Assert.assertEquals(calendar.getTimeInMillis(), calendar1.getTimeInMillis());
    }

    @Test
    public void testClone() {
        calendar.setTime(TEST_DATE);
        Calendar calendar1 = (Calendar) calendar.clone();

        Assert.assertEquals(calendar, calendar1);
        Assert.assertEquals(calendar.getTimeInMillis(), calendar1.getTimeInMillis());
    }

    @Test
    public void testGetAvailableLocales() {
        Locale[] locales = Calendar.getAvailableLocales();
        Assert.assertTrue(Arrays.stream(locales)
                .anyMatch(locale -> locale.equals(new Locale("ru", "RU"))));
    }

    @Test
    public void testSetTimeZone() {
        calendar.setTimeZone(TimeZone.getDefault());
    }

    @Test
    public void testGetTimeZone() {
        TimeZone tz = calendar.getTimeZone();
        //TODO: write assertion for the timezone
    }

    @Test
    public void testToInstant() {
        Instant instant = calendar.toInstant();
        //TODO: write assertion for instant
    }
}
