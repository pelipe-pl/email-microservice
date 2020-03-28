package pl.pelipe.emailmicroservice.email;

import com.sendgrid.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

import static pl.pelipe.emailmicroservice.config.keys.Keys.*;
import static pl.pelipe.emailmicroservice.email.EmailUtils.anonymize;

@Service
public class SendGridEmailService {

    private final Environment environment;
    private Logger logger = LoggerFactory.getLogger(SendGridEmailService.class);

    public SendGridEmailService(Environment environment) {
        this.environment = environment;
    }

    void send(String fromAddress, String senderName, String toAddress, String subject, String content) {

        Mail mail = new Mail(new Email(fromAddress, senderName), subject, new Email(toAddress), new Content("text/html", content));
        SendGrid sendGrid = new SendGrid(environment.getProperty("SENDGRID_API_KEY"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            logger.info(String.format(LOG_SENDGRID_EMAIL_SENDING_INFO, anonymize(toAddress)));
            Response response = sendGrid.api(request);
            logger.info(String.format(LOG_SENDGRID_RESPONSE_CODE, response.getStatusCode()));
            if (!response.getBody().isEmpty()) {
                logger.info(String.format(LOG_SENDGRID_RESPONSE_BODY, response.getBody()));
            }
            logger.debug(String.format(LOG_SENDGRID_RESPONSE_HEADERS, response.getHeaders()));
        } catch (IOException ex) {
            logger.error(String.format(LOG_SENDGRID_FAIL, fromAddress, anonymize(toAddress), subject));
            logger.error(Arrays.toString(ex.getStackTrace()));
        }
    }
}