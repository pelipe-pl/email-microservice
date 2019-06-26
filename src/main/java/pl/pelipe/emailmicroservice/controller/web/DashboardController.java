package pl.pelipe.emailmicroservice.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.pelipe.emailmicroservice.dashboard.TokenStatsScheduledService;
import pl.pelipe.emailmicroservice.dashboard.UserStatsScheduledService;


@Controller
public class DashboardController {

    private UserStatsScheduledService userStatsService;
    private TokenStatsScheduledService tokenStatsService;

    public DashboardController(UserStatsScheduledService userStatsService, TokenStatsScheduledService tokenStatsScheduledService) {
        this.userStatsService = userStatsService;
        this.tokenStatsService = tokenStatsScheduledService;
    }

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String home(Model model) {

        model.addAttribute("userStatsMap", userStatsService.getUserStatsMap());
        model.addAttribute("tokenStatsMap", tokenStatsService.getTokenStats());

        return "home";
    }
}
