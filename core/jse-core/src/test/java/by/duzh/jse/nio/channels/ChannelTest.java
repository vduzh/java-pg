package by.duzh.jse.nio.channels;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static by.duzh.jse.nio.etc.Params.*;

public class ChannelTest {
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testCreateWithStream() {
        File file = new File(JAVA_HOME_DIR, FILE_NAME);

        try (FileInputStream is = new FileInputStream(file); Channel channel = is.getChannel()) {
            ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testCreateWithFiles() {
        try (Channel channel = Files.newByteChannel(Paths.get(FILE_PATH))) {
            ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testIsOpen() {
        try (Channel channel = Files.newByteChannel(Paths.get(FILE_PATH))) {
            Assertions.assertTrue(channel.isOpen());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
