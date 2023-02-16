package vaspiakou.citylistapplication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vaspiakou.citylistapplication.dto.request.UsernameAndPasswordDto;
import vaspiakou.citylistapplication.dto.response.AccessTokenDto;
import vaspiakou.citylistapplication.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/auth")
    public AccessTokenDto loginWithNameAndPassword(@RequestBody UsernameAndPasswordDto usernameAndPasswordDto) {
        return authService.loginWithUsernameAndPassword(usernameAndPasswordDto);
    }
}
