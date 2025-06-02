package by.duzh.jse.nio.file.attribute;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.util.Set;

import static by.duzh.jse.nio.etc.Params.FILE_PATH;
import static by.duzh.jse.nio.etc.Params.JAVA_HOME_DIR;

@Disabled
public class PosixFileAttributesTest {
    private PosixFileAttributes dirAttributes, fileAttributes;

    @BeforeEach
    public void init() throws IOException {
        //TODO: throws UnsupportedOperationException in windows
        dirAttributes = Files.readAttributes(Paths.get(JAVA_HOME_DIR), PosixFileAttributes.class);
        fileAttributes = Files.readAttributes(Paths.get(FILE_PATH), PosixFileAttributes.class);
    }

    @Test
    public void testGroup() {
        GroupPrincipal group = fileAttributes.group();
    }

    @Test
    public void testOwner() {
        UserPrincipal owner = fileAttributes.owner();
    }

    @Test
    public void testPermissions() {
        Set<PosixFilePermission> permissions = fileAttributes.permissions();
    }
}