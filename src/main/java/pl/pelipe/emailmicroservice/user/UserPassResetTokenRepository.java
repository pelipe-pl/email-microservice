package pl.pelipe.emailmicroservice.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPassResetTokenRepository extends JpaRepository<UserPassResetTokenEntity, Long> {

    List<UserPassResetTokenEntity> getAllByActiveIsTrueAndUser(UserEntity userEntity);
}
