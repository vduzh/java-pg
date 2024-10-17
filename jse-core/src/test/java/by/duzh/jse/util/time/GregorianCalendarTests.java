package by.duzh.jse.util.time;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

//TODO: there are several methods that needs to be tested
public class GregorianCalendarTests {
    private GregorianCalendar calendar;

    @Before
    public void init() {
        calendar = new GregorianCalendar();
    }

    @Test
    public void testCreateDefault() {
        calendar = new GregorianCalendar();
    }

    @Test
    public void testCreate() {
        calendar = new GregorianCalendar(2020, Calendar.MAY, 9);
        calendar = new GregorianCalendar(2020, Calendar.MAY, 9, 10, 30);
        calendar = new GregorianCalendar(2020, Calendar.MAY, 9, 10, 30, 15);
    }

    @Test
    public void testCreateWithLocaleAndTimeZone() {
        calendar = new GregorianCalendar(Locale.getDefault());
        calendar = new GregorianCalendar(TimeZone.getDefault());
        calendar = new GregorianCalendar(TimeZone.getDefault(), Locale.getDefault());

        // TODO: How Locale and Timezone affects the Calendar
    }

    @Test
    public void testGetCalendarType() {
        Assert.assertEquals("gregory", calendar.getCalendarType());
    }

    @Test
    public void testCreateIsLeapYear() {
        Assert.assertTrue(calendar.isLeapYear(2020));
        Assert.assertFalse(calendar.isLeapYear(2021));
    }

    @Test
    public void testFrom() {
        //TODO: implement
        //GregorianCalendar.from()
    }

    @Test
    public void testZonedDateTime() {
        //TODO: implement
        //calendar.toZonedDateTime()
    }
}
