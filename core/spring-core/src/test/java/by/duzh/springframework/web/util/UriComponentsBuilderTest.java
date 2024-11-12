package by.duzh.springframework.web.util;

import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UriComponentsBuilderTest {

    @Test
    void fromUriString() {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString("https://example.com/hotels/{hotel}")
                .buildAndExpand("123");
        assertEquals("https://example.com/hotels/123", uriComponents.toUriString());
    }

    @Test
    void fromHttpUrl() {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl("https://example.com/hotels/{hotel}")
                .queryParam("status", "{status}")
                .buildAndExpand("123", "Open");
        assertEquals("https://example.com/hotels/123?status=Open", uriComponents.toUriString());
    }

    @Test
    void testPath() {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl("https://example.com/hotels")
                .path("/123")
                .build();

        assertEquals("https://example.com/hotels/123", uriComponents.toUriString());
    }

    @Test
    void test() {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl("https://example.com:8080/hotels/123?status=Open&typeId=20").build();
        assertEquals("https", uriComponents.getScheme());
        assertEquals("example.com", uriComponents.getHost());
        //assertEquals("foo", uriComponents.getFragment());
        assertEquals("/hotels/123", uriComponents.getPath());
        assertEquals("status=Open&typeId=20", uriComponents.getQuery());
        //assertEquals("foo", uriComponents.getSchemeSpecificPart());
        //assertEquals("foo", uriComponents.getUserInfo());
        assertEquals(2, uriComponents.getPathSegments().size());
        uriComponents.getPathSegments().containsAll(Arrays.asList("hotels", "123"));
        assertEquals(8080, uriComponents.getPort());
        assertEquals("{status=[Open], typeId=[20]}", uriComponents.getQueryParams().toString());
    }
}
