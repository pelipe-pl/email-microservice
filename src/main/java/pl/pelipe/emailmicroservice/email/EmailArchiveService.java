package pl.pelipe.emailmicroservice.email;

import com.sendgrid.Response;
import com.sendgrid.helpers.mail.Mail;

public interface EmailArchiveService {

    void save(EmailArchiveEntity emailArchiveEntity);

    EmailArchiveEntity createEmailArchive(Mail mail);

    void updateStatus(EmailArchiveEntity email, Response response);
}
