package vaspiakou.citylistapplication.exception.unauthorized;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import vaspiakou.citylistapplication.util.ResponseMessageConstants;

public class ExpiredAccessTokenException extends ResponseStatusException {
    public ExpiredAccessTokenException() {
        super(HttpStatus.UNAUTHORIZED, ResponseMessageConstants.EXPIRED_TOKEN);
    }
}
