package pl.pelipe.emailmicroservice.token;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import pl.pelipe.emailmicroservice.email.EmailBody;
import pl.pelipe.emailmicroservice.email.EmailService;

import java.time.LocalDateTime;

import static pl.pelipe.emailmicroservice.config.keys.Keys.*;

@Service
public class TokenService {

    private final TokenRepository repository;
    private final EmailService emailService;
    private final Environment environment;
    private Logger logger = LoggerFactory.getLogger(TokenService.class);

    public TokenService(TokenRepository repository, EmailService emailService, Environment environment) {
        this.repository = repository;
        this.emailService = emailService;
        this.environment = environment;
    }

    public TokenInfoDto create(String tokenOwnerEmail) {

        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setTokenValue(RandomStringUtils.randomAlphabetic(20));
        tokenEntity.setDailyUsageCounter(0L);
        tokenEntity.setCreatedAt(LocalDateTime.now());
        tokenEntity.setLastUsed(null);
        tokenEntity.setDailyUsageLimit(100L);
        tokenEntity.setValidUntil(LocalDateTime.now().plusDays(90));
        tokenEntity.setIsActive(true);
        tokenEntity.setOwnerEmail(tokenOwnerEmail);
        repository.save(tokenEntity);
        logger.info(String.format(LOG_TOKEN_CREATED, tokenOwnerEmail));
        notifyTokenOwner(tokenEntity);
        return toDto(tokenEntity);
    }

    public TokenInfoDto getTokenInfo(String token) {

        TokenEntity tokenEntity = repository.getByTokenValue(token);
        return toDto(tokenEntity);
    }

    public boolean existByTokenValue(String token) {

        boolean result = repository.existsByTokenValue(token);
        if (!result) logger.warn(String.format(LOG_TOKEN_NOT_EXISTING, token));
        return result;
    }

    private void notifyTokenOwner(TokenEntity tokenEntity) {
        EmailBody emailBody = new EmailBody();
        emailBody.setSenderName(environment.getProperty("EMAIL_DEFAULT_SENDER_NAME"));
        emailBody.setFromAddress(environment.getProperty("EMAIL_DEFAULT_SENDER_ADDRESS"));
        emailBody.setToAddress(tokenEntity.getOwnerEmail());
        emailBody.setSubject(EMAIL_SUBJECT_NEW_TOKEN_NOTIFY);
        emailBody.setContent(String.format(EMAIL_CONTENT_NEW_TOKEN_NOTIFY, tokenEntity.getTokenValue()));
        emailService.send(emailBody);
    }

    private TokenInfoDto toDto(TokenEntity tokenEntity) {
        TokenInfoDto tokenInfoDto = new TokenInfoDto();
        tokenInfoDto.setOwnerEmail(tokenEntity.getOwnerEmail());
        tokenInfoDto.setValue(tokenEntity.getTokenValue());
        tokenInfoDto.setIsActive(tokenEntity.getIsActive());
        tokenInfoDto.setCreatedAt(tokenEntity.getCreatedAt());
        tokenInfoDto.setDailyUsageCounter(tokenEntity.getDailyUsageCounter());
        tokenInfoDto.setLastUsed(tokenEntity.getLastUsed());
        tokenInfoDto.setValidUntil(tokenEntity.getValidUntil());
        tokenInfoDto.setDailyUsageLimit(tokenEntity.getDailyUsageLimit());
        return tokenInfoDto;
    }
}