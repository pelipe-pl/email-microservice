package pl.pelipe.emailmicroservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<TokenInfoDto> getInfo(@PathVariable String token) {
        if (tokenService.existByTokenValue(token))
            return new ResponseEntity<>(tokenService.getTokenInfo(token), HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}