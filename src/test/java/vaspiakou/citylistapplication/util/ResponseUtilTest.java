package vaspiakou.citylistapplication.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ResponseUtil.class})
@ExtendWith(SpringExtension.class)
class ResponseUtilTest {
    @Autowired
    private ResponseUtil responseUtil;

    @Test
    void testCreateGeneralErrorResponse() {
        assertEquals("Not all who wander are lost",
                responseUtil.createGeneralErrorResponse("Not all who wander are lost").getMessage());
    }
}

