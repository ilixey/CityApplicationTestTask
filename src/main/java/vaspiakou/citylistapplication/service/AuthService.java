package vaspiakou.citylistapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vaspiakou.citylistapplication.dto.request.UsernameAndPasswordDto;
import vaspiakou.citylistapplication.dto.response.AccessTokenDto;
import vaspiakou.citylistapplication.security.JwtTokenUtil;
import vaspiakou.citylistapplication.security.JwtUserDetailsService;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    public AccessTokenDto loginWithUsernameAndPassword(UsernameAndPasswordDto usernameAndPasswordDto){
        authenticate(usernameAndPasswordDto.getUsername(), usernameAndPasswordDto.getPassword());
        final UserDetails userDetails = jwtUserDetailsService
                .loadUserByUsername(usernameAndPasswordDto.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return new AccessTokenDto(token);
    }

    private void authenticate(String username, String password){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new DisabledException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }
    }
}
