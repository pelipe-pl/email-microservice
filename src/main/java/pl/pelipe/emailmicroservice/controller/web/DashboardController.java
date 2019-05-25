package pl.pelipe.emailmicroservice.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;


@Controller
public class DashboardController {

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String home(Model model, Principal principal) {

        return "home";
    }
}
