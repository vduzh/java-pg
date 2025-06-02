package by.duzh.junit5;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

// package level
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CoreFeaturesTest {
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
    void shouldPass() {
        assertTrue(true);
    }

    @Test
    void shouldFail() {
        assertThrows(Error.class, Assertions::fail);
    }

    @Test
    void shouldFailWithCustomErrorMessage() {
        assertThrows(Error.class, () -> {
            assertTrue(false, () -> "Something went wrong");
        });
    }

    @RepeatedTest(2)
    void shouldRepeatedTestTwice(RepetitionInfo info) {
        assertTrue(true);
    }
}
