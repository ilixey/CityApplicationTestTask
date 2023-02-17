package vaspiakou.citylistapplication.exception.unauthorized;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import vaspiakou.citylistapplication.util.ResponseMessageConstants;

public class InvalidAccessTokenException extends ResponseStatusException {
    public InvalidAccessTokenException() {
        super(HttpStatus.UNAUTHORIZED, ResponseMessageConstants.INVALID_TOKEN);
    }
}
