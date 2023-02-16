package vaspiakou.citylistapplication.util;

import org.springframework.stereotype.Component;
import vaspiakou.citylistapplication.dto.response.GeneralErrorResponse;

import java.time.Instant;

@Component
public class ResponseUtil {
    public GeneralErrorResponse createGeneralErrorResponse(String message) {
        return GeneralErrorResponse.builder()
                .dateTime(Instant.now())
                .message(message)
                .build();
    }
}
