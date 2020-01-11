package pl.pelipe.emailmicroservice.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pelipe.emailmicroservice.user.UserEntity;
import pl.pelipe.emailmicroservice.user.UserService;
import pl.pelipe.emailmicroservice.user.UserValidator;

import java.security.Principal;

@Controller
public class UserWebController {

    private final UserService userService;
    private final UserValidator userValidator;

    public UserWebController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @ModelAttribute
    public void getUser(Model model, Principal principal) {
        if (principal != null)
            model.addAttribute("user", userService.getByUsername(principal.getName()));
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout, Principal principal) {
        if (error != null) {
            model.addAttribute("error", "Invalid username/password!");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out.");
        }
        if (principal != null) return "redirect:/";
        else
            return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registration(Model model, Principal principal) {
        if (principal != null) return "redirect:/";
        model.addAttribute("userForm", new UserEntity());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") UserEntity userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult.toString());
            return "register";
        }
        userService.save(userForm);
        model.addAttribute("message", "You have been successfully registered. Your account is awaiting for activation.");
        return "login";
    }
}