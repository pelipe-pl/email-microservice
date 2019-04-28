package pl.pelipe.emailmicroservice.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class DashboardController {

    @GetMapping(value = {"/", "/home"})
    public String home(Model model, Principal principal) {
        return "home";
    }
}
