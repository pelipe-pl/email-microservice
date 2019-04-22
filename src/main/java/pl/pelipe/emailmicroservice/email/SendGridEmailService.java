package pl.pelipe.emailmicroservice.email;

import com.sendgrid.*;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SendGridEmailService {

    private final Environment environment;

    public SendGridEmailService(Environment environment) {
        this.environment = environment;
    }

    public Boolean send(String fromAddress, String senderName, String toAddress, String subject, String content) {

        Boolean result = Boolean.FALSE;
        Mail mail = new Mail(new Email(fromAddress, senderName), subject, new Email(toAddress), new Content("text/html", content));
        SendGrid sendGrid = new SendGrid(environment.getProperty("SENDGRID_API_KEY"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
            result = true;
        } catch (IOException ex) {
            System.out.println(this.getClass().getSimpleName() + " has failed to send following email.");
            System.out.println("from = [" + fromAddress + " to = [" + toAddress + "], subject = [" + subject);
            ex.printStackTrace();
        }
        return result;
    }
}
