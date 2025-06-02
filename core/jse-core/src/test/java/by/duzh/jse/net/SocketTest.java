package by.duzh.jse.net;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

@Disabled
public class SocketTest {
    private static final String TEST_HOST_NAME = "www.google.com";

    Socket socket;

    @BeforeEach
    public void init() {
    }

    @Test
    @EnabledIfSystemProperty(named = "test.network", matches = "true")
    public void testCreate() {
        try (Socket socket = new Socket(TEST_HOST_NAME, 80)) {
            ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Socket socket = new Socket(InetAddress.getByName(TEST_HOST_NAME), 80)) {
            ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @EnabledIfSystemProperty(named = "test.network", matches = "true")
    public void testGet() {
        try (Socket socket = new Socket(TEST_HOST_NAME, 80)) {
            boolean isConnected = socket.isConnected();
            Assertions.assertTrue(isConnected);

            boolean isBound = socket.isBound();
            Assertions.assertTrue(isBound);

            InetAddress inetAddress = socket.getInetAddress();
            Assertions.assertEquals(TEST_HOST_NAME, inetAddress.getHostName());

            int port = socket.getPort();
            Assertions.assertTrue(port > 0);
            Assertions.assertEquals(80, port);

            port = socket.getLocalPort();
            Assertions.assertTrue(port > -1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @EnabledIfSystemProperty(named = "test.network", matches = "true")
    public void testConnect() throws IOException {
        try (Socket socket = new Socket(TEST_HOST_NAME, 80)) {
            // TODO: test connect
            //socket.connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetInputStream() {
        //try (Socket socket = new Socket("www.whois.com", 43)) {
        try (Socket socket = new Socket(TEST_HOST_NAME, 80)) {
            BufferedInputStream is = new BufferedInputStream(socket.getInputStream());
            BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream());

            os.write("Hello".getBytes());

            int c;
            // TODO: read doesn't work!!! Fix it.
            while ((c = is.read()) != -1) {
                System.out.print((char) c);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetOutputStream() {
        try (Socket socket = new Socket(TEST_HOST_NAME, 80)) {
            BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream());

            String s = "Hello";
            os.write(s.getBytes());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
