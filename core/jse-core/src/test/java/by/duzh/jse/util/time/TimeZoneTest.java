package by.duzh.jse.util.time;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

//TODO: Write some absent tests
public class TimeZoneTest {
    private TimeZone timeZone;

    private static final String CURRENT_TIMEZONE_ID = "Europe/Minsk";

    @BeforeEach
    public void init() {
        timeZone = TimeZone.getDefault();
    }

    @Test
    public void testGeID() {
        String id = timeZone.getID();
        Assertions.assertEquals(CURRENT_TIMEZONE_ID, id);
    }

    @Test
    public void testGetAvailableIDs() {
        String[] availableIDs = TimeZone.getAvailableIDs();
        Assertions.assertTrue(availableIDs.length > 0);
        Assertions.assertTrue(Arrays.asList(availableIDs).contains(CURRENT_TIMEZONE_ID));

        // Получаем ID для смещения UTC+3
        availableIDs = TimeZone.getAvailableIDs(3 * 60 * 60 * 1000);
        Assertions.assertTrue(availableIDs.length > 0);
        Assertions.assertTrue(Arrays.asList(availableIDs).contains("Europe/Moscow"));
    }

    @Test
    public void testGetOffset() {
        int offset = timeZone.getOffset(1, 2020, Calendar.OCTOBER, 27, Calendar.WEDNESDAY, 55);
        Assertions.assertEquals(timeZone.getRawOffset(), offset);
    }

    @Test
    public void testGetRawOffset() {
        int offset = timeZone.getRawOffset();
        Assertions.assertEquals(3 * 60 * 60 * 1000, offset); // UTC+3 для Europe/Minsk
    }

    @Test
    public void testGetTimeZone() {
        TimeZone timeZone = TimeZone.getTimeZone(CURRENT_TIMEZONE_ID);
        Assertions.assertEquals(CURRENT_TIMEZONE_ID, timeZone.getID());
        Assertions.assertEquals(3 * 60 * 60 * 1000, timeZone.getRawOffset());
    }

    @Test
    public void testInDaylightTime() {
        boolean isDST = timeZone.inDaylightTime(new Date());
        Assertions.assertFalse(isDST); // Europe/Minsk не использует DST с 2011 года
    }

    @Test
    public void testSetDefault() {
        TimeZone originalTimeZone = TimeZone.getDefault();
        try {
            TimeZone newTimeZone = TimeZone.getTimeZone("Europe/Berlin");
            TimeZone.setDefault(newTimeZone);
            Assertions.assertEquals("Europe/Berlin", TimeZone.getDefault().getID());
        } finally {
            TimeZone.setDefault(originalTimeZone);
        }
    }

    @Test
    public void testSetId() {
        String originalId = timeZone.getID();
        timeZone.setID("Europe/London");
        Assertions.assertEquals("Europe/London", timeZone.getID());
        timeZone.setID(originalId);
    }

    @Test
    public void testSetRawOffset() {
        int originalOffset = timeZone.getRawOffset();
        timeZone.setRawOffset(2 * 60 * 60 * 1000); // UTC+2
        Assertions.assertEquals(2 * 60 * 60 * 1000, timeZone.getRawOffset());
        timeZone.setRawOffset(originalOffset);
    }

    @Test
    public void testToZoneId() {
        java.time.ZoneId zoneId = timeZone.toZoneId();
        Assertions.assertEquals(CURRENT_TIMEZONE_ID, zoneId.getId());
    }

    @Test
    public void testUseDaylightTime() {
        boolean usesDST = timeZone.useDaylightTime();
        Assertions.assertFalse(usesDST); // Europe/Minsk не использует DST с 2011 года
    }

    @Test
    public void testClone() {
        TimeZone clonedTimeZone = (TimeZone) timeZone.clone();
        Assertions.assertEquals(timeZone.getID(), clonedTimeZone.getID());
        Assertions.assertEquals(timeZone.getRawOffset(), clonedTimeZone.getRawOffset());
    }

}
