package pl.pelipe.emailmicroservice.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pl.pelipe.emailmicroservice.user.UserDetailsServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static pl.pelipe.emailmicroservice.config.keys.Keys.LOG_USER_LOGGED_IN;

@Component
public class AuthenticationSuccessHandlerImpl extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private UserDetailsServiceImpl userDetailsService;
    private Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandlerImpl.class);

    public AuthenticationSuccessHandlerImpl(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        String username = authentication.getName();
        if (!username.isEmpty()) {
            userDetailsService.updateLastLogon(username);
            logger.info(String.format(LOG_USER_LOGGED_IN, authentication.getName()));
        }
        super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
    }
}
