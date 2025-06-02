package by.duzh.jse.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ScannerTest {
    private Scanner scanner;

    @AfterEach
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

        Assertions.assertTrue(scanner.hasNext());
        Assertions.assertEquals("foo", scanner.next());

        Assertions.assertTrue(scanner.hasNextBoolean());
        Assertions.assertTrue(scanner.nextBoolean());

        Assertions.assertTrue(scanner.hasNextInt());
        Assertions.assertEquals(15, scanner.nextInt());

        Assertions.assertTrue(scanner.hasNext());
        Assertions.assertEquals("scanning", scanner.next());

        Assertions.assertTrue(scanner.hasNextDouble());
        Assertions.assertEquals(10.99, scanner.nextDouble(), 0);

        Assertions.assertFalse(scanner.hasNext());
    }

    @Test
    public void testUseDelimiters() {
        scanner = new Scanner("1, 111, Department1");
        scanner.useDelimiter(", *");

        Assertions.assertEquals(1, scanner.nextInt());
        Assertions.assertEquals(111, scanner.nextInt());
        Assertions.assertEquals("Department1", scanner.next());
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

                Assertions.assertEquals(i, lineScanner.nextInt());
                Assertions.assertEquals("" + i + i + i, lineScanner.next());
                Assertions.assertEquals("Department" + i, lineScanner.next());
            }
            i++;
        }
    }

    @Test
    public void testFindLine() {
        scanner = new Scanner("Apple: 10 Orange: 4 Lemon: 2");

        if (scanner.findInLine("Orange: ") != null) {
            if (scanner.hasNext()) {
                Assertions.assertEquals(4, scanner.nextInt());
            }
        }
    }

    @Test
    public void testFindWithinHorizon() {
        scanner = new Scanner("Apple: 10 Orange: 4 Lemon: 2");

        if (scanner.findWithinHorizon("Orange: ", 18) != null) {
            Assertions.assertEquals(4, scanner.nextInt());
        }
    }

    @Test
    public void testSkip() {
        scanner = new Scanner("Apple: 10 Orange: 4 Lemon: 2");
        try (Scanner scanner2 = scanner.skip(Pattern.compile(".*Orange: "))) {
            Assertions.assertEquals("4 Lemon: 2", scanner2.nextLine());
        }
    }
}
