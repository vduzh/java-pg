package by.vduzh.pg.dto.foo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FooDtoTest {

    public static final Integer TEST_ID = 1;
    public static final String TEST_NAME = "test";

    @Test
    void shouldCreateNewDto() {
        var foo = new FooDto(null, TEST_NAME);

        assertThat(foo.id()).isNull();
        assertThat(foo.name()).isEqualTo(TEST_NAME);
    }

    @Test
    void shouldCreateNewDtoWithId() {
        var foo = new FooDto(TEST_ID, TEST_NAME);

        assertThat(foo.id()).isEqualTo(TEST_ID);
        assertThat(foo.name()).isEqualTo(TEST_NAME);
    }

}