package pl.pelipe.emailmicroservice.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import pl.pelipe.emailmicroservice.user.UserDetailsServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static pl.pelipe.emailmicroservice.config.keys.Keys.LOG_USER_AUTHENTICATION_FAILED;

@Component
public class AuthenticationFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private UserDetailsServiceImpl userDetailsService;
    private Logger logger = LoggerFactory.getLogger(AuthenticationFailureHandlerImpl.class);

    public AuthenticationFailureHandlerImpl(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String username = httpServletRequest.getParameter("username");
        userDetailsService.updateLastLogonFailure(username);
        logger.warn(String.format(LOG_USER_AUTHENTICATION_FAILED,
                username,
                e.getMessage(),
                httpServletRequest.getRemoteAddr()));

        super.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
    }
}
