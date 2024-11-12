package by.duzh.jse.nio.etc;

public class Params {

    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    public static final String JAVA_HOME_DIR = System.getProperty("java.home");
    public static final String FILE_NAME = "release";
    public static final String FILE_PATH = JAVA_HOME_DIR + System.getProperty("file.separator") + FILE_NAME;

    public static final String TMP_DIR = System.getProperty("java.io.tmpdir");
}
