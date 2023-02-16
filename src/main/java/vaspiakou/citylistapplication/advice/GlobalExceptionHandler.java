package vaspiakou.citylistapplication.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import vaspiakou.citylistapplication.dto.response.GeneralErrorResponse;
import vaspiakou.citylistapplication.dto.response.ResponseMessageConstants;
import vaspiakou.citylistapplication.exception.FileErrorException;
import vaspiakou.citylistapplication.exception.notfound.CityNotFoundException;
import vaspiakou.citylistapplication.exception.notfound.RoleNotFoundException;
import vaspiakou.citylistapplication.exception.notfound.UserNotFoundException;
import vaspiakou.citylistapplication.exception.unauthorized.ExpiredAccessTokenException;
import vaspiakou.citylistapplication.exception.unauthorized.InvalidAccessTokenException;
import vaspiakou.citylistapplication.util.ResponseUtil;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {
    private final ResponseUtil responseUtil;

    @ExceptionHandler({
            CityNotFoundException.class,
            UserNotFoundException.class,
            RoleNotFoundException.class
    })
    public ResponseEntity<GeneralErrorResponse> handleAllNotFoundException(ResponseStatusException ex) {
        GeneralErrorResponse response = responseUtil.createGeneralErrorResponse(ex.getReason());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            ExpiredAccessTokenException.class,
            InvalidAccessTokenException.class
    })
    public ResponseEntity<GeneralErrorResponse> handleUnauthorizedException(ResponseStatusException ex) {
        GeneralErrorResponse response = responseUtil.createGeneralErrorResponse(ex.getReason());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler()
    public ResponseEntity<GeneralErrorResponse> handleAllException(Exception ex){
        GeneralErrorResponse response = responseUtil.createGeneralErrorResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileErrorException.class)
    public ResponseEntity<GeneralErrorResponse> handleFileErrorException(Exception ex){
        GeneralErrorResponse response = responseUtil.createGeneralErrorResponse(ResponseMessageConstants.FILE_ERROR);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
