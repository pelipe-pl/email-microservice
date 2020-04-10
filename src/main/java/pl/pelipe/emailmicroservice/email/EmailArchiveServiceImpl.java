package pl.pelipe.emailmicroservice.email;

import com.sendgrid.Response;
import com.sendgrid.helpers.mail.Mail;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailArchiveServiceImpl implements EmailArchiveService {

    private final EmailArchiveRepository emailArchiveRepository;

    public EmailArchiveServiceImpl(EmailArchiveRepository emailArchiveRepository) {
        this.emailArchiveRepository = emailArchiveRepository;
    }

    @Override
    public void save(EmailArchiveEntity emailArchiveEntity) {
        emailArchiveRepository.save(emailArchiveEntity);
    }

    public EmailArchiveEntity createEmailArchive(Mail mail) {
        EmailArchiveEntity email = new EmailArchiveEntity();

        email.setStatus(EmailStatus.NEW);
        email.setLastUpdate(LocalDateTime.now());
        email.setCreatedAt(LocalDateTime.now());
        email.setFromAddress(mail.getFrom().getEmail());
        email.setFromName(mail.getFrom().getName());
        email.setContent(mail.getContent().get(0).getValue());
        email.setProvider("SendGrid");
        email.setSubject(mail.getSubject());
        email.setToAddress(mail.getPersonalization().get(0).getTos().get(0).getEmail());
        email.setSendRetry(0);
        save(email);

        return email;
    }

    public void updateStatus(EmailArchiveEntity email, Response response) {
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
        save(email);
    }
}
