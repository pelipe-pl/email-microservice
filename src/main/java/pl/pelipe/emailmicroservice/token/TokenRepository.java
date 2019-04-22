package pl.pelipe.emailmicroservice.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    TokenEntity getByTokenValue(String tokenValue);

    boolean existsByTokenValue(String tokenValue);
}
