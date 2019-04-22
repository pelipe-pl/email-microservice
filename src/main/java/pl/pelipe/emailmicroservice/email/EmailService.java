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

    public int send(String token, EmailBody emailBody) {

        int validationResult = tokenService.validate(token);
        if (validationResult == 0) {
            Boolean result = sendGridEmailService.send(
                    emailBody.getFromAddress(),
                    emailBody.getSenderName(),
                    emailBody.getToAddress(),
                    emailBody.getSubject(),
                    emailBody.getContent());
            if (result) return 0;
            else return 3;
        }
        if (validationResult == 1) return 1;
        if (validationResult == 2) return 2;
        return 3;
    }
}