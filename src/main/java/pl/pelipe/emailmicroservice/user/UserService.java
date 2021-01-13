package pl.pelipe.emailmicroservice.user;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pelipe.emailmicroservice.email.EmailBody;
import pl.pelipe.emailmicroservice.email.EmailService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import static pl.pelipe.emailmicroservice.config.keys.Keys.*;
import static pl.pelipe.emailmicroservice.email.EmailUtils.anonymize;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;
    private final UserPassResetTokenRepository userPassResetTokenRepository;
    private final EmailService emailService;
    private final Environment environment;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository, UserPassResetTokenRepository userPassResetTokenRepository, EmailService emailService, Environment environment) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
        this.userPassResetTokenRepository = userPassResetTokenRepository;
        this.emailService = emailService;
        this.environment = environment;
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

    public Boolean sendPasswordResetToken(String username) {
        UserEntity userEntity = getByUsername(username);
        if (userEntity == null) {
            logger.info("Failed to generate password reset token. " + username + " does not exist.");
            return false;
        } else {
            List<UserPassResetTokenEntity> oldTokens = userPassResetTokenRepository.getAllByActiveIsTrueAndUser(userEntity);
            if (!oldTokens.isEmpty()) {
                oldTokens.forEach(userPassResetTokenEntity -> userPassResetTokenEntity.setActive(false));
                userPassResetTokenRepository.saveAll(oldTokens);
            }
            UserPassResetTokenEntity tokenEntity = new UserPassResetTokenEntity();
            String tokenValue = RandomStringUtils.randomAlphanumeric(50);
            tokenEntity.setTokenValue(tokenValue);
            tokenEntity.setExpiryDate(LocalDateTime.now().plusHours(24));
            tokenEntity.setActive(true);
            tokenEntity.setUser(userEntity);
            userPassResetTokenRepository.save(tokenEntity);

            EmailBody emailBody = new EmailBody();
            emailBody.setContent(PASSWORD_RESET_EMAIL_CONTENT + generatePasswordResetUrlLink(tokenValue));
            emailBody.setSubject(PASSWORD_RESET_EMAIL_SUBJECT);
            emailBody.setSenderName(environment.getRequiredProperty(PASSWORD_RESET_EMAIL_SENDER_NAME));
            emailBody.setFromAddress(environment.getRequiredProperty(PASSWORD_RESET_EMAIL_SENDER_ADDRESS));
            emailBody.setToAddress(userEntity.getUsername());

            return emailService.send(emailBody);
        }
    }

    private String generatePasswordResetUrlLink(String tokenValue) {
        return " <a href=" + '"'
                + environment.getRequiredProperty(PASSWORD_RESET_EMAIL_URL)
                + "?token=" + tokenValue
                + '"' + ">HERE</a>";
    }
}