package pl.pelipe.emailmicroservice.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    TokenEntity getByTokenValue(String tokenValue);

    boolean existsByTokenValue(String tokenValue);

    Long countAllByIsActiveOrValidUntilAfter(Boolean isActive, LocalDateTime validUntil);

    Long countAllByCreatedAtAfter(LocalDateTime createdAt);
}
