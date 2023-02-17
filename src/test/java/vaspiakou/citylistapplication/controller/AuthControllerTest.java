package vaspiakou.citylistapplication.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import vaspiakou.citylistapplication.advice.GlobalExceptionHandler;
import vaspiakou.citylistapplication.dto.request.UsernameAndPasswordDto;
import vaspiakou.citylistapplication.dto.response.AccessTokenDto;
import vaspiakou.citylistapplication.exception.notfound.NotFoundException;
import vaspiakou.citylistapplication.security.JwtFilter;
import vaspiakou.citylistapplication.security.JwtTokenUtil;
import vaspiakou.citylistapplication.security.JwtUserDetailsService;
import vaspiakou.citylistapplication.security.WebSecurityConfig;
import vaspiakou.citylistapplication.service.AuthService;
import vaspiakou.citylistapplication.util.ResponseUtil;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureWebMvc
@SpringBootTest(classes = {
        WebSecurityConfig.class,
        JwtUserDetailsService.class,
        GlobalExceptionHandler.class,
        ResponseUtil.class,
        AuthController.class,
        AuthService.class,
        JwtTokenUtil.class,
        JwtFilter.class
}, properties = "spring.cloud.config.enabled=false")
@MockBeans({
        @MockBean(ResponseUtil.class),
        @MockBean(AuthService.class),
        @MockBean(JwtUserDetailsService.class),
        @MockBean(JwtTokenUtil.class)
})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthControllerTest {

    private final MockMvc mockMvc;
    private final AuthService authService;
    private final String AUTH_URL = "/api/v1/auth";
    private final String TEST_STRING = "test";

    @Test
    void loginWithValidPasswordShouldReturnOkStatus() throws Exception {
        UsernameAndPasswordDto usernameAndPasswordDto = createUsernameAndPasswordDto();
        String json = convertRequestDtoToJson(usernameAndPasswordDto);
        AccessTokenDto accessTokenDto = createAccessTokenDto();
        when(authService.loginWithUsernameAndPassword(usernameAndPasswordDto)).thenReturn(accessTokenDto);
        mockMvc.perform(post(AUTH_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
        verify(authService, times(1)).loginWithUsernameAndPassword(usernameAndPasswordDto);
    }

    @Test
    void loginWithInValidPasswordShouldReturnUserNotFound() throws Exception {
        UsernameAndPasswordDto usernameAndPasswordDto = createUsernameAndPasswordDto();
        String json = convertRequestDtoToJson(usernameAndPasswordDto);
        when(authService.loginWithUsernameAndPassword(usernameAndPasswordDto)).thenThrow(new NotFoundException(TEST_STRING));
        mockMvc.perform(post(AUTH_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    private <T> String convertRequestDtoToJson(T requestDto) {
        Gson gson = new Gson();
        return gson.toJson(requestDto);
    }

    private UsernameAndPasswordDto createUsernameAndPasswordDto(){
        return UsernameAndPasswordDto.builder()
                .username(TEST_STRING)
                .password(TEST_STRING)
                .build();
    }
    private AccessTokenDto createAccessTokenDto(){
        return AccessTokenDto.builder()
                .accessToken(TEST_STRING)
                .build();
    }
}
