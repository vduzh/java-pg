package by.duzh.jse.util.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.*;

public class CalendarTests {
    private static final Date TEST_DATE = new Date(999999999999L); // Sep 09 04:46:39 MSD 2001

    private Calendar calendar;

    @BeforeEach
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
        Assertions.assertEquals(2001, calendar.get(Calendar.YEAR));
        Assertions.assertEquals(Calendar.SEPTEMBER, calendar.get(Calendar.MONTH));
        Assertions.assertEquals(9, calendar.get(Calendar.DAY_OF_MONTH));
        Assertions.assertEquals(4, calendar.get(Calendar.HOUR_OF_DAY));
        Assertions.assertEquals(46, calendar.get(Calendar.MINUTE));
        Assertions.assertEquals(39, calendar.get(Calendar.SECOND));
        Assertions.assertEquals(999, calendar.get(Calendar.MILLISECOND));
    }

    @Test
    public void testGetTime() {
        calendar.setTime(TEST_DATE);
        Assertions.assertEquals(TEST_DATE, calendar.getTime());
    }

    @Test
    public void testIsSet() {
        calendar.setTime(TEST_DATE);
        Assertions.assertTrue(calendar.isSet(Calendar.YEAR));

        calendar.clear();
        Assertions.assertFalse(calendar.isSet(Calendar.YEAR));
    }

    @Test
    public void testAdd() {
        calendar.setTime(TEST_DATE);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
    }

    @Test
    public void testAfter() {
        calendar.setTime(TEST_DATE);
        Assertions.assertTrue(Calendar.getInstance().after(calendar));
    }

    @Test
    public void testBefore() {
        calendar.setTime(TEST_DATE);
        Assertions.assertTrue(calendar.before(Calendar.getInstance()));
    }

    @Test
    public void testClear() {
        calendar.clear();

        Assertions.assertFalse(calendar.isSet(Calendar.YEAR));
        Assertions.assertEquals(1970, calendar.get(Calendar.YEAR));
        Assertions.assertEquals(0, calendar.get(Calendar.MONTH));
        Assertions.assertEquals(1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testEquals() {
        calendar.setTime(TEST_DATE);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(TEST_DATE);

        Assertions.assertEquals(calendar, calendar1);
        Assertions.assertEquals(calendar.getTimeInMillis(), calendar1.getTimeInMillis());
    }

    @Test
    public void testClone() {
        calendar.setTime(TEST_DATE);
        Calendar calendar1 = (Calendar) calendar.clone();

        Assertions.assertEquals(calendar, calendar1);
        Assertions.assertEquals(calendar.getTimeInMillis(), calendar1.getTimeInMillis());
    }

    @Test
    public void testGetAvailableLocales() {
        Locale[] locales = Calendar.getAvailableLocales();
        Assertions.assertTrue(Arrays.stream(locales)
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
