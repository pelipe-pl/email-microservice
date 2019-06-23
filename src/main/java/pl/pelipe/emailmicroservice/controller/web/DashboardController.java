package pl.pelipe.emailmicroservice.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pelipe.emailmicroservice.dashboard.UserStatsScheduledService;

import java.security.Principal;


@Controller
public class DashboardController {

    private UserStatsScheduledService userStatsService;

    public DashboardController(UserStatsScheduledService userStatsService) {
        this.userStatsService = userStatsService;
    }

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String home(Model model, Principal principal) {

        model.addAttribute("userStatsMap", userStatsService.getUserStatsMap());

        return "home";
    }
}
