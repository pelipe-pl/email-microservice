package pl.pelipe.emailmicroservice.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pelipe.emailmicroservice.token.TokenInfoDto;
import pl.pelipe.emailmicroservice.token.TokenService;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/token")
public class TokenWebController {

    private final TokenService tokenService;

    public TokenWebController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping
    public String tokenForm(Model model) {
        model.addAttribute("tokenInfo", new TokenInfoDto());
        return "newTokenForm";
    }

    @PostMapping
    public String createToken(Model model, @Valid @ModelAttribute TokenInfoDto tokenInfoDto,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult);
            return "newTokenForm";
        } else {
            model.addAttribute("tokenInfo", tokenService.create(tokenInfoDto.getOwnerEmail()));
            return "newTokenInfo";
        }
    }
}