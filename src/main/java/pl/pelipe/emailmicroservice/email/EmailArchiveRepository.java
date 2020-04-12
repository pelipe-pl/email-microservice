package pl.pelipe.emailmicroservice.email;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailArchiveRepository extends JpaRepository<EmailArchiveEntity, Long> {

    List<EmailArchiveEntity> getAllByStatusAndSendRetryLessThan(EmailStatus status, int sendRetry);
}
