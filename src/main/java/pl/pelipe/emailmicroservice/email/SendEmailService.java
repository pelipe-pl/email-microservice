package pl.pelipe.emailmicroservice.email;

public interface SendEmailService {

    void send(String fromAddress, String senderName, String toAddress, String subject, String content);
}
