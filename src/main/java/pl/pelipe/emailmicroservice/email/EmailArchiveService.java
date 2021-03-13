package pl.pelipe.emailmicroservice.email;

import com.sendgrid.helpers.mail.Mail;
import pl.pelipe.emailmicroservice.token.TokenEntity;

public interface EmailArchiveService {

    void save(EmailArchiveEntity emailArchiveEntity);

    EmailArchiveEntity createWithToken(Mail mail, TokenEntity tokenEntity);

    EmailArchiveEntity create(Mail mail);
}