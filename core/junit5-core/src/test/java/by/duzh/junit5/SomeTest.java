package by.duzh.junit5;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

// package level
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SomeTest {

    @BeforeAll
    void setUpClass() {
        System.out.println("before all is working");
    }

    @BeforeEach
    void setUp() {
        System.out.println("before each is working");
    }

    @AfterEach
    void tearDown() {
        System.out.println("after each is working");
    }

    @AfterAll
    void tearDownClass() {
        System.out.println("after all is working");
    }

    // package level
    @Test
    void ok() {
        assertTrue(true);
    }

    @Disabled
    @Test
    void fail() {
        Assertions.fail();
    }

    @Disabled
    @Test
    void failWithCustomErrorMessage() {
        assertTrue(false, () -> "Something went wrong");
    }

    @RepeatedTest(2)
    void repeatedTest(RepetitionInfo info) {
        System.out.println(info.getCurrentRepetition());
        assertTrue(true);
    }
}
