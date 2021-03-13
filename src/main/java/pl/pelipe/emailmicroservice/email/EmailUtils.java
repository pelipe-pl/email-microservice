package pl.pelipe.emailmicroservice.email;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import pl.pelipe.emailmicroservice.token.TokenEntity;

import static pl.pelipe.emailmicroservice.config.keys.Keys.EMAIL_CONTENT_NEW_TOKEN_NOTIFY;
import static pl.pelipe.emailmicroservice.config.keys.Keys.EMAIL_SUBJECT_NEW_TOKEN_NOTIFY;

@Component
public class EmailUtils {
    private final EmailService emailService;
    private final Environment environment;

    public EmailUtils(EmailService emailService, Environment environment) {
        this.emailService = emailService;
        this.environment = environment;
    }

    public static String anonymize(String email) {
        return "****" + email.substring(4);
    }

    public void notifyTokenOwner(TokenEntity tokenEntity) {
        EmailBody emailBody = new EmailBody();
        emailBody.setSenderName(environment.getProperty("EMAIL_DEFAULT_SENDER_NAME"));
        emailBody.setFromAddress(environment.getProperty("EMAIL_DEFAULT_SENDER_ADDRESS"));
        emailBody.setToAddress(tokenEntity.getOwnerEmail());
        emailBody.setSubject(EMAIL_SUBJECT_NEW_TOKEN_NOTIFY);
        emailBody.setContent(String.format(EMAIL_CONTENT_NEW_TOKEN_NOTIFY, tokenEntity.getTokenValue()));
        emailService.send(emailBody, null);
    }
}