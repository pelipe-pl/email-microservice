package pl.pelipe.emailmicroservice.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static pl.pelipe.emailmicroservice.config.keys.Keys.LOG_USER_LOGGED_OUT;

@Component
public class LogoutSuccessHandlerImpl extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(LogoutSuccessHandlerImpl.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        if (authentication != null) {
            String username = authentication.getName();
            if (!username.isEmpty()) {
                logger.info(String.format(LOG_USER_LOGGED_OUT, username));
            }
        }
        super.onLogoutSuccess(httpServletRequest, httpServletResponse, authentication);
    }
}
