package by.duzh.jse.util.regex;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class PatternTest {
    @Test
    public void compile() throws Exception {
        String text = "333-44-55";
        Pattern pattern = Pattern.compile("\\d{3}-\\d{2}-\\d{2}");
        Matcher matcher = pattern.matcher(text);
        assertTrue(matcher.find());
    }

    @Test
    public void testJDK11AsMatchPredicate() throws Exception {
        throw new Exception("testJDK11AsMatchPredicate");
    }
}
