package by.duzh.jse.util.time;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

//TODO: Write some absent tests
public class TimeZoneTest {
    private TimeZone timeZone;

    private static final String CURRENT_TIMEZONE_ID = "Europe/Minsk";

    @Before
    public void init() {
        timeZone = TimeZone.getDefault();
    }

    @Test
    public void testGeID() {
        String id = timeZone.getID();
        Assert.assertEquals(CURRENT_TIMEZONE_ID, id);
    }

    @Test
    public void testGetAvailableIDs() {
        String[] availableIDs = TimeZone.getAvailableIDs();
        Assert.assertTrue(Arrays.asList(availableIDs).contains(CURRENT_TIMEZONE_ID));

        //TODO: the
        availableIDs = TimeZone.getAvailableIDs(2);
        // System.out.println(Arrays.asList(availableIDs));
        Assert.assertTrue(Arrays.asList(availableIDs).contains(CURRENT_TIMEZONE_ID));
    }

    @Test
    public void testGetOffset() {
        int offset = timeZone.getOffset(1, 2020, Calendar.OCTOBER, 27, Calendar.WEDNESDAY, 55);
    }

    @Test
    public void testGetRawOffset() {
        int offset = timeZone.getRawOffset();
    }

    @Test
    public void testGetTimeZone() {
        TimeZone timeZone = TimeZone.getTimeZone(CURRENT_TIMEZONE_ID);
    }

    @Test
    public void testInDaylightTime() {
        boolean b = timeZone.inDaylightTime(new Date());
    }

    @Test
    public void testSetDefault() {
        TimeZone newTimeZone = TimeZone.getTimeZone("Europe/Berlin");
        TimeZone.setDefault(newTimeZone);
    }

    @Test
    public void testSetId() {
        //TODO: not clear how to use it
        //timeZone.setID();
    }

    @Test
    public void testSetRawOffset() {
        //TODO: not clear how to use it
        //timeZone.setRawOffset();
    }

    @Test
    public void testToZoneId() {
        //TODO: write tests
        timeZone.toZoneId();
    }

    @Test
    public void testUseDaylightTime() {
        //TODO: write tests
        timeZone.useDaylightTime();
    }

    @Test
    public void testClone() {
        timeZone.clone();
    }

}
