package by.duzh.jse.nio.channels;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.file.Files;
import java.nio.file.Paths;

import static by.duzh.jse.nio.etc.Params.*;

public class ChannelTest {
    @Test
    public void testCreateWithStream() {
        File file = new File(JAVA_HOME_DIR, FILE_NAME);

        try (FileInputStream is = new FileInputStream(file); Channel channel = is.getChannel()) {
            ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCreateWithFiles() {
        try (Channel channel = Files.newByteChannel(Paths.get(FILE_PATH))) {
            ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testIsOpen() {
        try (Channel channel = Files.newByteChannel(Paths.get(FILE_PATH))) {
            Assert.assertTrue(channel.isOpen());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
