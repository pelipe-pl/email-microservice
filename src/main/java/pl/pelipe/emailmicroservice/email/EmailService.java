package pl.pelipe.emailmicroservice.email;

import org.springframework.stereotype.Service;
import pl.pelipe.emailmicroservice.token.TokenValidator;

import javax.validation.constraints.NotNull;

import static pl.pelipe.emailmicroservice.email.ResponseStatus.*;

@Service
public class EmailService {

    private final SendGridEmailService sendGridEmailService;
    private final TokenValidator tokenValidator;

    public EmailService(SendGridEmailService sendGridEmailService, TokenValidator tokenValidator, EmailArchiveService emailArchiveService) {
        this.sendGridEmailService = sendGridEmailService;
        this.tokenValidator = tokenValidator;
    }

    public ResponseStatus send(Payload payload) {

        @NotNull EmailBody emailBody = payload.getEmailBody();
        @NotNull String token = payload.getTokenValue();

        if (!tokenValidator.validate(token)) return TOKEN_ERROR;
        boolean result = send(emailBody);

        if (result) return OK;
        else return FAILED;
    }

    public boolean send(EmailBody emailBody) {

        return sendGridEmailService.send(
                emailBody.getFromAddress(),
                emailBody.getSenderName(),
                emailBody.getToAddress(),
                emailBody.getSubject(),
                emailBody.getContent());
    }
}