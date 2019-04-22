package pl.pelipe.emailmicroservice.email;

import org.springframework.stereotype.Service;
import pl.pelipe.emailmicroservice.token.TokenService;

@Service
public class EmailService {

    private final SendGridEmailService sendGridEmailService;
    private final TokenService tokenService;

    public EmailService(SendGridEmailService sendGridEmailService, TokenService tokenService) {
        this.sendGridEmailService = sendGridEmailService;
        this.tokenService = tokenService;
    }

    public boolean send(String token, EmailBody emailBody) {

        if (tokenService.validate(token)) {
            sendGridEmailService.send(
                    emailBody.getFromAddress(),
                    emailBody.getSenderName(),
                    emailBody.getToAddress(),
                    emailBody.getSubject(),
                    emailBody.getContent());
            return true;
        } else return false;
    }
}