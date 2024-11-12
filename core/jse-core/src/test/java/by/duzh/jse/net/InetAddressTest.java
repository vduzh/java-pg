package by.duzh.jse.net;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

public class InetAddressTest {
    private static final String TEST_HOST_NAME = "www.google.com";
    private InetAddress address;

    @Before
    public void init() throws UnknownHostException {
        address = InetAddress.getByName(TEST_HOST_NAME);
    }

    @Test
    public void testCreate() throws UnknownHostException {
        address = InetAddress.getLocalHost();
        //System.out.println(address);

        address = InetAddress.getByName(TEST_HOST_NAME);
        //System.out.println(address);

        InetAddress[] addresses = InetAddress.getAllByName(TEST_HOST_NAME);
        Assert.assertTrue(addresses.length > 0);

        //address = InetAddress.getByAddress("172.217.18.100");
        //System.out.println(address);
    }

    @Test
    public void testGetAddress() throws UnknownHostException {
        for (byte b : address.getAddress()) {
            // transfer to init and get unsigned value
            int i = b & 0xff;
            Assert.assertTrue(i >= 0 && i <= 256);
        }

        String hostAddress = address.getHostAddress(); // 172.217.18.100
        Pattern pattern = Pattern.compile("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$");
        Assert.assertTrue(pattern.matcher(hostAddress).find());

        Assert.assertEquals(TEST_HOST_NAME, address.getHostName());

        Assert.assertFalse(address.isMulticastAddress());

        // etc...
    }
}
