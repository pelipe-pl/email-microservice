package pl.pelipe.emailmicroservice.email;

import com.sendgrid.helpers.mail.Mail;

public interface EmailArchiveService {

    void save(EmailArchiveEntity emailArchiveEntity);

    EmailArchiveEntity create(Mail mail);
}