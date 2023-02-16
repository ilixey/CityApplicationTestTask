package vaspiakou.citylistapplication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import vaspiakou.citylistapplication.dto.request.UsernameAndPasswordDto;
import vaspiakou.citylistapplication.security.JwtTokenUtil;
import vaspiakou.citylistapplication.security.JwtUserDetailsService;

@ContextConfiguration(classes = {AuthService.class})
@ExtendWith(SpringExtension.class)
class AuthServiceTest {
    @Autowired
    private AuthService authService;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;
    private final String TEST_STRING = "test";

    @Test
    void loginWithUsernameAndPasswordTest() throws AuthenticationException {
        when(jwtUserDetailsService.loadUserByUsername(any()))
                .thenReturn(new User(TEST_STRING, TEST_STRING, new ArrayList<>()));
        when(jwtTokenUtil.generateToken(any())).thenReturn(TEST_STRING);
        when(authenticationManager.authenticate(any()))
                .thenReturn(new TestingAuthenticationToken(TEST_STRING, TEST_STRING));
        assertEquals(TEST_STRING,
                authService.loginWithUsernameAndPassword(new UsernameAndPasswordDto(TEST_STRING, TEST_STRING)).getAccessToken());
        verify(jwtUserDetailsService).loadUserByUsername(any());
        verify(jwtTokenUtil).generateToken(any());
        verify(authenticationManager).authenticate(any());
    }

    @Test
    void loginWithUsernameAndPasswordShouldThrowDisabledException() throws AuthenticationException {
        when(jwtUserDetailsService.loadUserByUsername(any()))
                .thenReturn(new User(TEST_STRING, TEST_STRING, new ArrayList<>()));
        when(jwtTokenUtil.generateToken(any())).thenReturn(TEST_STRING);
        when(authenticationManager.authenticate(any())).thenThrow(new DisabledException(TEST_STRING));
        assertThrows(DisabledException.class,
                () -> authService.loginWithUsernameAndPassword(new UsernameAndPasswordDto(TEST_STRING, TEST_STRING)));
        verify(authenticationManager).authenticate(any());
    }

    @Test
    void loginWithUsernameAndPasswordShouldThrowBadCredentialsException() throws AuthenticationException {
        when(jwtUserDetailsService.loadUserByUsername(any()))
                .thenReturn(new User(TEST_STRING, TEST_STRING, new ArrayList<>()));
        when(jwtTokenUtil.generateToken(any())).thenReturn(TEST_STRING);
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException(TEST_STRING));
        assertThrows(BadCredentialsException.class,
                () -> authService.loginWithUsernameAndPassword(new UsernameAndPasswordDto(TEST_STRING, TEST_STRING)));
        verify(authenticationManager).authenticate(any());
    }
}

