package pl.pelipe.emailmicroservice.email;

import com.sendgrid.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

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
            Response response = sendGrid.api(request);
            logger.info("SendGrid response code: [" + response.getStatusCode() + "]");
            logger.info("SendGrid response body: [" + response.getBody() + "]");
            logger.info("SendGrid response headers: [" + response.getHeaders() + "]");
        } catch (IOException ex) {
            logger.error(this.getClass().getSimpleName() + " has failed to send email from = ["
                    + fromAddress + " to = [" + toAddress + "], subject = [" + subject);
            logger.error(Arrays.toString(ex.getStackTrace()));
        }
    }
}