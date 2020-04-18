package pl.pelipe.emailmicroservice.email;

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

    @Override
    public EmailArchiveEntity create(Mail mail) {
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
}