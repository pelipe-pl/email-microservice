package pl.pelipe.emailmicroservice.email;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailArchiveRepository extends JpaRepository<EmailArchiveEntity, Long> {
}
