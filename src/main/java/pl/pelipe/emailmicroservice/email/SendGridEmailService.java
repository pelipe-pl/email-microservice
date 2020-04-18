package pl.pelipe.emailmicroservice.email;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

import static pl.pelipe.emailmicroservice.config.keys.Keys.*;
import static pl.pelipe.emailmicroservice.email.EmailUtils.anonymize;

@Service
public class SendGridEmailService implements SendEmailService {

    private final Environment environment;
    private final EmailArchiveService emailArchiveService;
    private final Logger logger = LoggerFactory.getLogger(SendGridEmailService.class);

    public SendGridEmailService(Environment environment, EmailArchiveService emailArchiveService1) {
        this.environment = environment;
        this.emailArchiveService = emailArchiveService1;
    }

    public boolean send(EmailArchiveEntity emailArchiveEntity) {
        Response response = sendRequest(buildSendGridMail(emailArchiveEntity));
        updateStatus(emailArchiveEntity, response);
        return response.getStatusCode() == 202;
    }

    public boolean send(String fromAddress, String senderName, String toAddress, String subject, String content) {
        Mail sendGridEmail = buildSendGridMail(fromAddress, senderName, toAddress, subject, content);
        EmailArchiveEntity emailArchiveEntity = emailArchiveService.create(sendGridEmail);
        Response response = sendRequest(buildSendGridMail(emailArchiveEntity));
        updateStatus(emailArchiveEntity, response);
        return response.getStatusCode() == 202;
    }

    public boolean resend(EmailArchiveEntity emailArchiveEntity) {
        if (emailArchiveEntity.getStatus() == EmailStatus.PENDING && emailArchiveEntity.getSendRetry() <= 3) {
            Response response = sendRequest(buildSendGridMail(emailArchiveEntity));
            int sendRetry = emailArchiveEntity.getSendRetry();
            int responseCode = response.getStatusCode();
            if (responseCode == 202) {
                emailArchiveEntity.setStatus(EmailStatus.SENT);
                emailArchiveEntity.setLastUpdate(LocalDateTime.now());
                emailArchiveEntity.setSuccessSent(LocalDateTime.now());
                emailArchiveEntity.setProviderResponse(responseCode);
                emailArchiveEntity.setProviderId(response.getHeaders().get("X-Message-Id"));
                emailArchiveService.save(emailArchiveEntity);
                return true;
            } else {
                sendRetry = sendRetry + 1;
                emailArchiveEntity.setLastUpdate(LocalDateTime.now());
                emailArchiveEntity.setProviderResponse(responseCode);
                emailArchiveEntity.setSendRetry(sendRetry);
                if (sendRetry == 3) {
                    emailArchiveEntity.setStatus(EmailStatus.FAILED);
                }
                emailArchiveService.save(emailArchiveEntity);
                return false;
            }
        }
        return false;
    }

    private Response sendRequest(Mail sendGridEmail) {
        SendGrid sendGrid = new SendGrid(environment.getProperty("SENDGRID_API_KEY"));
        Request request = new Request();
        Response response = new Response();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(sendGridEmail.build());
            logger.info(String.format(LOG_SENDGRID_EMAIL_SENDING_INFO, anonymize(sendGridEmail.getPersonalization().get(0).getTos().get(0).getEmail())));
            response = sendGrid.api(request);
            logger.info(String.format(LOG_SENDGRID_RESPONSE_CODE, response.getStatusCode()));
            logger.info("SendGrid X-Message-Id: " + response.getHeaders().get("X-Message-Id"));
            logger.debug(String.format(LOG_SENDGRID_RESPONSE_HEADERS, response.getHeaders()));
        } catch (IOException ex) {
            logger.error(String.format(LOG_SENDGRID_FAIL, sendGridEmail.getFrom().getEmail(), sendGridEmail.getPersonalization().get(0).getTos().get(0).getEmail()), ex);
        }
        return response;
    }

    private void updateStatus(EmailArchiveEntity emailArchiveEntity, Response response) {
        int statusCode = response.getStatusCode();
        emailArchiveEntity.setProviderResponse(statusCode);
        emailArchiveEntity.setLastUpdate(LocalDateTime.now());
        if (response.getHeaders() != null) emailArchiveEntity.setProviderId(response.getHeaders().get("X-Message-Id"));
        if (statusCode == 202) {
            emailArchiveEntity.setStatus(EmailStatus.SENT);
            emailArchiveEntity.setSuccessSent(LocalDateTime.now());
        } else {
            emailArchiveEntity.setStatus(EmailStatus.PENDING);
            emailArchiveEntity.setSendRetry(1);
        }
        emailArchiveService.save(emailArchiveEntity);
    }

    private Mail buildSendGridMail(EmailArchiveEntity emailArchiveEntity) {
        return new Mail(
                new Email(emailArchiveEntity.getFromAddress(), emailArchiveEntity.getFromName()),
                emailArchiveEntity.getSubject(),
                new Email(emailArchiveEntity.getToAddress()),
                new Content("text/html", emailArchiveEntity.getContent()));
    }

    private Mail buildSendGridMail(String fromAddress, String senderName, String toAddress, String subject, String content) {
        return new Mail(
                new Email(fromAddress, senderName),
                subject,
                new Email(toAddress),
                new Content("text/html", content));
    }
}