package vaspiakou.citylistapplication.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CityNotFoundException extends ResponseStatusException {
    public CityNotFoundException(String name){
        super(HttpStatus.NOT_FOUND, String.format("City with name %s is not found.", name));
    }

    public CityNotFoundException(Long id){
        super(HttpStatus.NOT_FOUND, String.format("City with id %d is not found.", id));
    }
}
