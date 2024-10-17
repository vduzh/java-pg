package by.duzh.jse.util;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ScannerTest {
    private Scanner scanner;

    @After
    public void tearDown() {
        if (scanner != null) {
            scanner.close();
        }
    }

    @Test
    public void testCreate() {
        //TODO: from File
        //TODO: from io

        // from String
        String s = "10 99.88 scanning is easy";
        scanner = new Scanner(s);
    }

    @Test
    public void testHasNextAndNext() {
        scanner = new Scanner("foo true 15 scanning 10,99");

        Assert.assertTrue(scanner.hasNext());
        Assert.assertEquals("foo", scanner.next());

        Assert.assertTrue(scanner.hasNextBoolean());
        Assert.assertTrue(scanner.nextBoolean());

        Assert.assertTrue(scanner.hasNextInt());
        Assert.assertEquals(15, scanner.nextInt());

        Assert.assertTrue(scanner.hasNext());
        Assert.assertEquals("scanning", scanner.next());

        Assert.assertTrue(scanner.hasNextDouble());
        Assert.assertEquals(10.99, scanner.nextDouble(), 0);

        Assert.assertFalse(scanner.hasNext());
    }

    @Test
    public void testUseDelimiters() {
        scanner = new Scanner("1, 111, Department1");
        scanner.useDelimiter(", *");

        Assert.assertEquals(1, scanner.nextInt());
        Assert.assertEquals(111, scanner.nextInt());
        Assert.assertEquals("Department1", scanner.next());
    }

    @Test
    public void testNextLine() {
        scanner = new Scanner("1, 111, Department1\n2, 222, Department2");

        int i = 1;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            //System.out.printf("Line: %s%n", line);
            try (Scanner lineScanner = new Scanner(line)) {
                lineScanner.useDelimiter(", *");

                Assert.assertEquals(i, lineScanner.nextInt());
                Assert.assertEquals("" + i + i + i, lineScanner.next());
                Assert.assertEquals("Department" + i, lineScanner.next());
            }
            i++;
        }
    }

    @Test
    public void testFindLine() {
        scanner = new Scanner("Apple: 10 Orange: 4 Lemon: 2");

        if (scanner.findInLine("Orange: ") != null) {
            if (scanner.hasNext()) {
                Assert.assertEquals(4, scanner.nextInt());
            }
        }
    }

    @Test
    public void testFindWithinHorizon() {
        scanner = new Scanner("Apple: 10 Orange: 4 Lemon: 2");

        if (scanner.findWithinHorizon("Orange: ", 18) != null) {
            Assert.assertEquals(4, scanner.nextInt());
        }
    }

    @Test
    public void testSkip() {
        scanner = new Scanner("Apple: 10 Orange: 4 Lemon: 2");
        try (Scanner scanner2 = scanner.skip(Pattern.compile(".*Orange: "))) {
            Assert.assertEquals("4 Lemon: 2", scanner2.nextLine());
        }
    }
}
