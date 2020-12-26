package pl.pelipe.emailmicroservice.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;

import static pl.pelipe.emailmicroservice.config.keys.Keys.LOG_USER_REGISTRATION;
import static pl.pelipe.emailmicroservice.config.keys.Keys.LOG_USER_UPDATE;
import static pl.pelipe.emailmicroservice.email.EmailUtils.anonymize;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    public void save(UserEntity userEntity) {
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(new HashSet<>(roleRepository.getByName("user")));
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setLastChange(LocalDateTime.now());
        userEntity.setIsActive(false);
        userRepository.save(userEntity);
        logger.info(String.format(LOG_USER_REGISTRATION, anonymize(userEntity.getUsername()), userEntity.getId().toString()));
    }

    public void update(UserEntity updatedUser) {
        UserEntity userEntity = getById(updatedUser.getId());
        if (updatedUser.getFirstName() != null) userEntity.setFirstName(updatedUser.getFirstName());
        if (updatedUser.getLastName() != null) userEntity.setLastName(updatedUser.getLastName());
        userEntity.setLastChange(LocalDateTime.now());
        userRepository.save(userEntity);
        logger.info(String.format(LOG_USER_UPDATE, anonymize(userEntity.getUsername())));
    }

    public UserEntity getById(Long id) {
        return userRepository.getById(id);
    }

    public UserEntity getByUsername(String username) {
        return userRepository.getByUsername(username);
    }
}