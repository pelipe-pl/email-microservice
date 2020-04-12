package pl.pelipe.emailmicroservice.email;

public interface SendEmailService {

    boolean send(String fromAddress, String senderName, String toAddress, String subject, String content);

    boolean send(EmailArchiveEntity emailArchiveEntity);

    boolean resend(EmailArchiveEntity emailArchiveEntity);
}
