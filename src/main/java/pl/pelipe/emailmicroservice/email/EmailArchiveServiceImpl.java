package pl.pelipe.emailmicroservice.email;

import com.sendgrid.helpers.mail.Mail;
import org.springframework.stereotype.Service;
import pl.pelipe.emailmicroservice.token.TokenEntity;

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
        EmailArchiveEntity email = constructEmailArchiveEntity(mail);
        save(email);
        return email;
    }

    @Override
    public EmailArchiveEntity createWithToken(Mail mail, TokenEntity tokenEntity) {
        EmailArchiveEntity email = constructEmailArchiveEntity(mail);
        email.setToken(tokenEntity);
        save(email);
        return email;
    }

    private EmailArchiveEntity constructEmailArchiveEntity(Mail mail) {
        EmailArchiveEntity emailArchiveEntity = new EmailArchiveEntity();
        emailArchiveEntity.setStatus(EmailStatus.NEW);
        emailArchiveEntity.setLastUpdate(LocalDateTime.now());
        emailArchiveEntity.setCreatedAt(LocalDateTime.now());
        emailArchiveEntity.setFromAddress(mail.getFrom().getEmail());
        emailArchiveEntity.setFromName(mail.getFrom().getName());
        emailArchiveEntity.setContent(mail.getContent().get(0).getValue());
        emailArchiveEntity.setProvider("SendGrid");
        emailArchiveEntity.setSubject(mail.getSubject());
        emailArchiveEntity.setToAddress(mail.getPersonalization().get(0).getTos().get(0).getEmail());
        emailArchiveEntity.setSendRetry(0);
        return emailArchiveEntity;
    }
}