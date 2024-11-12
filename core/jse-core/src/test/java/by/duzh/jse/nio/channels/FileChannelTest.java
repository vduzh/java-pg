package by.duzh.jse.nio.channels;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

import static by.duzh.jse.nio.etc.Params.FILE_PATH;
import static by.duzh.jse.nio.etc.Params.TMP_DIR;

public class FileChannelTest {

    @Test
    public void testExplicitChannelRead() {
        try (SeekableByteChannel channel = Files.newByteChannel(Paths.get(FILE_PATH))) {
            StringBuilder sb = new StringBuilder();

            ByteBuffer buffer = ByteBuffer.allocate(16);
            int count;
            while ((count = channel.read(buffer)) != -1) {
                buffer.rewind();
                for (int i = 0; i < count; i++) {
                    sb.append((char) buffer.get());
                }
            }
            //System.out.println(sb);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testMappedChannelRead() {
        try (FileChannel channel = (FileChannel) Files.newByteChannel(Paths.get(FILE_PATH))) {
            StringBuilder sb = new StringBuilder();

            // get the size of the file
            long fileSize = channel.size();
            // Map the file to the buffer
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, fileSize);

            // Map the file to the buffer
            for (int i = 0; i < fileSize; i++) {
                sb.append((char) buffer.get());
            }

            //System.out.println(sb);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testExplicitChannelWrite() {
        Path path = Paths.get(TMP_DIR + UUID.randomUUID() + ".txt");
        // Open file for writing
        try (FileChannel fileChannel = (FileChannel) Files.newByteChannel(path,
                StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
            // create a buffer
            ByteBuffer buffer = ByteBuffer.allocate(16);

            // write bytes to the buffer
            for (int i = 0; i < 16; i++) {
                buffer.put((byte) 'A');
            }

            // write buffer to the output file
            fileChannel.write(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testMappedChannelWrite() {
        Path path = Paths.get(TMP_DIR + UUID.randomUUID() + ".txt");

        // Open file for writing
        try (FileChannel fileChannel = (FileChannel) Files.newByteChannel(path,
                StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE)) {

            String[] strings = {"Hello ", "World", "!"};
            for (int i = 0, pos = 0; i < strings.length; i++) {
                MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, pos, strings[i].length());
                buffer.put(strings[i].getBytes());

                pos += strings[i].length();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}