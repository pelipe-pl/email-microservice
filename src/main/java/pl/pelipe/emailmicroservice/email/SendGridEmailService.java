package pl.pelipe.emailmicroservice.email;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

import static pl.pelipe.emailmicroservice.config.keys.Keys.*;
import static pl.pelipe.emailmicroservice.email.EmailUtils.anonymize;

@Service
public class SendGridEmailService implements SendEmailService {

    private final Environment environment;
    private final EmailArchiveService emailArchiveService;
    private final Logger logger = LoggerFactory.getLogger(SendGridEmailService.class);

    public SendGridEmailService(Environment environment, EmailArchiveService emailArchiveService, EmailArchiveService emailArchiveService1) {
        this.environment = environment;
        this.emailArchiveService = emailArchiveService1;
    }

    public void send(String fromAddress, String senderName, String toAddress, String subject, String content) {

        Mail sendGridEmail = new Mail(new Email(fromAddress, senderName), subject, new Email(toAddress), new Content("text/html", content));
        EmailArchiveEntity emailArchiveEntity = emailArchiveService.createEmailArchive(sendGridEmail);
        SendGrid sendGrid = new SendGrid(environment.getProperty("SENDGRID_API_KEY"));
        Request request = new Request();
        Response response = new Response();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(sendGridEmail.build());
            logger.info(String.format(LOG_SENDGRID_EMAIL_SENDING_INFO, anonymize(toAddress)));
            response = sendGrid.api(request);
            emailArchiveService.updateStatus(emailArchiveEntity, response);
            logger.info(String.format(LOG_SENDGRID_RESPONSE_CODE, response.getStatusCode()));
            if (!response.getBody().isEmpty()) {
                logger.info(String.format(LOG_SENDGRID_RESPONSE_BODY, response.getBody()));
            }
            logger.debug(String.format(LOG_SENDGRID_RESPONSE_HEADERS, response.getHeaders()));
        } catch (IOException ex) {
            logger.error(String.format(LOG_SENDGRID_FAIL, fromAddress, anonymize(toAddress), subject));
            logger.error(Arrays.toString(ex.getStackTrace()));
        }
        finally {
            emailArchiveService.updateStatus(emailArchiveEntity, response);
        }
    }


}