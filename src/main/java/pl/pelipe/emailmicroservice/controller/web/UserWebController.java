package pl.pelipe.emailmicroservice.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
    public String login(Principal principal) {
        if (principal != null) return "redirect:/";
        else
            return "login";
    }

    @RequestMapping(value = "/login-failure", method = RequestMethod.GET)
    public String loginFailure(Principal principal) {
        if (principal != null) return "redirect:/";
        else
            return "login-failure";
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
            StringBuilder errorMessages = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                if (error.getCode() != null) {
                    errorMessages.append(error.getCode()).append(" ");
                }
            }
            model.addAttribute("error", errorMessages);
            return "register";
        }
        userService.save(userForm);
        model.addAttribute("message", "You have been successfully registered. Your account is awaiting for activation.");
        return "login";
    }

    @RequestMapping(value = "/forgot-password", method = RequestMethod.GET)
    public String forgotPassword(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("user", new UserEntity());
            return "forgot-password";
        } else return "home";
    }

    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
    public String resetPassword(Model model, Principal principal, @ModelAttribute("user") UserEntity user) {
        if (principal == null && user != null && !user.getUsername().isEmpty()) {
            Boolean result = userService.sendPasswordResetToken(user.getUsername());
            if (result) {
                model.addAttribute("message", "Password reset email has been sent.");
                return "login";
            } else {
                model.addAttribute("error", "Password reset email send has failed.");
                return "forgot-password";
            }
        } else {
            model.addAttribute("error", "Cannot reset password when logged in.");
            return "/home";
        }
    }
}