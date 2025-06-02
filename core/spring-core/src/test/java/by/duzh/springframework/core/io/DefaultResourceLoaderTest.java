package by.duzh.springframework.core.io;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.springframework.util.ResourceUtils.*;

import java.util.logging.Logger;

public class DefaultResourceLoaderTest {
    private static final Logger logger = Logger.getLogger(DefaultResourceLoaderTest.class.getName());

    @Test
    public void test() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }
}