package vaspiakou.citylistapplication.advice;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vaspiakou.citylistapplication.dto.response.GeneralErrorResponse;
import vaspiakou.citylistapplication.exception.notfound.NotFoundException;
import vaspiakou.citylistapplication.util.ResponseMessageConstants;
import vaspiakou.citylistapplication.exception.unauthorized.ExpiredAccessTokenException;
import vaspiakou.citylistapplication.exception.unauthorized.InvalidAccessTokenException;
import vaspiakou.citylistapplication.util.ResponseUtil;
import java.time.Instant;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {
        GlobalExceptionHandler.class,
        ResponseUtil.class
})
@MockBeans(value = {@MockBean(ResponseUtil.class)})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GlobalExceptionHandlerTest {

    private final ResponseUtil responseUtil;
    private static GeneralErrorResponse response;
    private final GlobalExceptionHandler globalExceptionHandler;

    @BeforeAll
    static void createGeneralErrorResponse() {
        response = createGeneralErrorResponse("TEST_VALUE");
    }

    @Test
    void handleNotFoundException(){
        response.setMessage(ResponseMessageConstants.NOT_FOUND);
        when(responseUtil.createGeneralErrorResponse(anyString())).thenReturn(response);
        ResponseEntity<GeneralErrorResponse> responseEntity = globalExceptionHandler
                .handleAllNotFoundException(new NotFoundException(anyString()));
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(response.getMessage(), Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    @Test
    void handleInvalidTokenException(){
        response.setMessage(ResponseMessageConstants.INVALID_TOKEN);
        when(responseUtil.createGeneralErrorResponse(anyString())).thenReturn(response);
        ResponseEntity<GeneralErrorResponse> responseEntity = globalExceptionHandler
                .handleUnauthorizedException(new InvalidAccessTokenException());
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals(response.getMessage(), Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    @Test
    void handleExpiredTokenException(){
        response.setMessage(ResponseMessageConstants.EXPIRED_TOKEN);
        when(responseUtil.createGeneralErrorResponse(anyString())).thenReturn(response);
        ResponseEntity<GeneralErrorResponse> responseEntity = globalExceptionHandler
                .handleUnauthorizedException(new ExpiredAccessTokenException());
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals(response.getMessage(), Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    @Test
    void handleInternalServerErrorException(){
        response.setMessage(ResponseMessageConstants.INTERNAL_SERVER_ERROR);
        when(responseUtil.createGeneralErrorResponse(anyString())).thenReturn(response);
        ResponseEntity<GeneralErrorResponse> responseEntity = globalExceptionHandler
                .handleAllException(new Exception());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    private static GeneralErrorResponse createGeneralErrorResponse(String message) {
        return GeneralErrorResponse.builder()
                .dateTime(Instant.now())
                .message(message)
                .build();
    }
}
