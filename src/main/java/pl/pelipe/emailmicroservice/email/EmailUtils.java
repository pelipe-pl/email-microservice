package pl.pelipe.emailmicroservice.email;

import java.time.LocalDateTime;

public class EmailUtils {

    public static String anonymize(String email) {
        return "****" + email.substring(4);
    }

    public EmailArchiveEntity buildEntity(String provider, String fromAddress, String senderName, String toAddress, String subject, String content) {

        EmailArchiveEntity emailArchiveEntity = new EmailArchiveEntity();
        emailArchiveEntity.setProvider(provider);
        emailArchiveEntity.setFromAddress(fromAddress);
        emailArchiveEntity.setFromName(senderName);
        emailArchiveEntity.setToAddress(toAddress);
        emailArchiveEntity.setSubject(subject);
        emailArchiveEntity.setContent(content);
        emailArchiveEntity.setCreatedAt(LocalDateTime.now());
        emailArchiveEntity.setLastUpdate(LocalDateTime.now());
        emailArchiveEntity.setStatus(EmailStatus.NEW);
        return emailArchiveEntity;

    }
}