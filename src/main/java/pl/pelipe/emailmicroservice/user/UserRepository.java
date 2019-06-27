package pl.pelipe.emailmicroservice.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity getById(Long id);

    UserEntity getByUsername(String username);

    Long countAllByIsActive(Boolean isActive);

    Long countAllByCreatedAtAfter(LocalDateTime dateTime);
}
