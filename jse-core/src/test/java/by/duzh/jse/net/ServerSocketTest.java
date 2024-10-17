package by.duzh.jse.net;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketTest {
    @Before
    public void init() {
    }

    @Test
    public void testCreate() {
        // Create with the maximum of 50 connections
        try (ServerSocket socket = new ServerSocket(9999)) {
            ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ;

        // Create with specified maximum of connections
        try (ServerSocket socket = new ServerSocket(9999)) {
            ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ;

        //TODO: Create with the localAddress
/*
        try (ServerSocket socket = new ServerSocket(9999)) {
            ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        };
*/
    }

    @Test
    public void testAccept() {
        try (ServerSocket socket = new ServerSocket(9999)) {
            // TODO: write tests
            //Socket accept = socket.accept();
            // communicate with the client via the socket above

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ;
    }

}
