package by.duzh.jse.util.time;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

//NOTE: The remaining members are deprecated.
public class DateTests {
    private static final long TEST_DATE = 999999999999L; // Sep 09 04:46:39 MSD 2001

    @Test
    public void testCreateCurrent() {
        Date date = new Date();
    }

    @Test
    public void testCreateDate() {
        Date date = new Date(TEST_DATE);
    }

    @Test
    public void testGetTime() {
        Date date = new Date(TEST_DATE);
        Assert.assertEquals(TEST_DATE, date.getTime());
    }

    @Test
    public void testSetTime() {
        Date date = new Date();
        date.setTime(TEST_DATE);

        Assert.assertEquals(TEST_DATE, date.getTime());
    }

    @Test
    public void testAfter() {
        Assert.assertTrue(new Date().after(new Date(TEST_DATE)));
    }

    @Test
    public void testBefore() {
        Assert.assertTrue(new Date(TEST_DATE).before(new Date()));
    }

    @Test
    public void testCompareTo() {
        Date old = new Date(TEST_DATE);
        Date old2 = new Date(TEST_DATE);
        Date now = new Date();

        Assert.assertTrue(old.compareTo(now) < 0);
        Assert.assertTrue(now.compareTo(old) > 0);
        Assert.assertTrue(old.compareTo(old2) == 0);
    }

    @Test
    public void testEquals() {
        Date date1 = new Date(TEST_DATE);
        Date date2 = new Date(TEST_DATE);
        Date date3 = new Date();

        Assert.assertTrue(date1.equals(date2));
        Assert.assertFalse(date1.equals(date3));
    }

    @Test
    public void testClone() {
        Date date1 = new Date(TEST_DATE);
        Date date2 = (Date) date1.clone();

        Assert.assertEquals(date1, date2);
    }

    @Test
    public void testToString() {
        Date date = new Date(TEST_DATE);
        System.out.println(date);
    }
}
