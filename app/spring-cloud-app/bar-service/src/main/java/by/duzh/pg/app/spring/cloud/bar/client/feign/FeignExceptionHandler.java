package by.duzh.pg.app.spring.cloud.bar.client.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
// Add Custom handlers for Feign errors
public class FeignExceptionHandler implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        switch (response.status()) {
            case 404: {
                // see error.include-message props
                return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error 404 -> Bad Request");
            }
        }
        return null;
    }
}
