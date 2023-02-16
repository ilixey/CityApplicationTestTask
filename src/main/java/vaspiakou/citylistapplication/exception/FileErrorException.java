package vaspiakou.citylistapplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FileErrorException extends ResponseStatusException {
    public FileErrorException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
