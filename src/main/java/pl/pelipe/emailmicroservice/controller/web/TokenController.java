package pl.pelipe.emailmicroservice.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.pelipe.emailmicroservice.token.TokenInfoDto;
import pl.pelipe.emailmicroservice.token.TokenService;

@Controller(value = "/token")
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping
    public String tokenForm(Model model) {
        model.addAttribute("tokenInfo", new TokenInfoDto());
        return "newTokenForm";
    }

    @PostMapping
    public String createToken(Model model, @ModelAttribute TokenInfoDto tokenInfoDto) {
        model.addAttribute("tokenInfo", tokenService.create(tokenInfoDto.getOwnerEmail()));
        return "newTokenInfo";
    }
}