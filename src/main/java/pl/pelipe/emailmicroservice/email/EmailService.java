package pl.pelipe.emailmicroservice.email;

import org.springframework.stereotype.Service;
import pl.pelipe.emailmicroservice.token.TokenValidator;

@Service
public class EmailService {

    private final SendGridEmailService sendGridEmailService;
    private final TokenValidator tokenValidator;

    public EmailService(SendGridEmailService sendGridEmailService, TokenValidator tokenValidator) {
        this.sendGridEmailService = sendGridEmailService;
        this.tokenValidator = tokenValidator;

    }

    public boolean send(String token, EmailBody emailBody) {

        if (tokenValidator.validate(token)) {
            sendGridEmailService.send(
                    emailBody.getFromAddress(),
                    emailBody.getSenderName(),
                    emailBody.getToAddress(),
                    emailBody.getSubject(),
                    emailBody.getContent());
            return true;
        } else return false;
    }

    public void send(EmailBody emailBody) {
        sendGridEmailService.send(
                emailBody.getFromAddress(),
                emailBody.getSenderName(),
                emailBody.getToAddress(),
                emailBody.getSubject(),
                emailBody.getContent());
    }
}