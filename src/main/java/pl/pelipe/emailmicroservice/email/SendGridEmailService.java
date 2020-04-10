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
        EmailArchiveEntity emailArchiveEntity = createEmailArchive(sendGridEmail);
        SendGrid sendGrid = new SendGrid(environment.getProperty("SENDGRID_API_KEY"));
        Request request = new Request();
        Response response = new Response();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(sendGridEmail.build());
            logger.info(String.format(LOG_SENDGRID_EMAIL_SENDING_INFO, anonymize(toAddress)));
            response = sendGrid.api(request);
            updateStatus(emailArchiveEntity, response);
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
            updateStatus(emailArchiveEntity, response);
        }
    }

    private EmailArchiveEntity createEmailArchive(Mail mail) {
        EmailArchiveEntity email = new EmailArchiveEntity();

        email.setStatus(EmailStatus.NEW);
        email.setLastUpdate(LocalDateTime.now());
        email.setCreatedAt(LocalDateTime.now());
        email.setFromAddress(mail.getFrom().getEmail());
        email.setFromName(mail.getFrom().getName());
        email.setContent(mail.getContent().get(0).getValue().toString());
        email.setProvider("SendGrid");
        email.setSubject(mail.getSubject());
        email.setToAddress(mail.getPersonalization().get(0).getTos().get(0).getEmail());
        email.setSendRetry(0);
        return saveEmailArchive(email);
    }

    private EmailArchiveEntity saveEmailArchive(EmailArchiveEntity emailArchiveEntity) {
        emailArchiveService.save(emailArchiveEntity);
        return emailArchiveEntity;
    }

    private void updateStatus(EmailArchiveEntity email, Response response) {
        int statusCode = response.getStatusCode();
        email.setProviderResponse(statusCode);
        email.setLastUpdate(LocalDateTime.now());
        if (statusCode == 202) {
            email.setStatus(EmailStatus.SENT);
            email.setSuccessSent(LocalDateTime.now());
        } else {
            email.setStatus(EmailStatus.PENDING);
            email.setSendRetry(1);
        }
        saveEmailArchive(email);
    }
}