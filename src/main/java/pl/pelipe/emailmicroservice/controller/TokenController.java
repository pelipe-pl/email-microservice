package pl.pelipe.emailmicroservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.pelipe.emailmicroservice.token.TokenInfoDto;
import pl.pelipe.emailmicroservice.token.TokenService;

@RestController
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/info/{token}")
    public TokenInfoDto getInfo(@PathVariable String token) {
        return tokenService.getTokenInfo(token);
    }
}
