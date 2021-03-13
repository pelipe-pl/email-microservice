package pl.pelipe.emailmicroservice.token;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.pelipe.emailmicroservice.email.EmailUtils;

import java.time.LocalDateTime;

import static pl.pelipe.emailmicroservice.config.keys.Keys.LOG_TOKEN_CREATED;
import static pl.pelipe.emailmicroservice.config.keys.Keys.LOG_TOKEN_NOT_EXISTING;

@Service
public class TokenService {

    private final TokenRepository repository;
    private final Logger logger = LoggerFactory.getLogger(TokenService.class);
    private final EmailUtils emailUtils;

    public TokenService(TokenRepository repository, EmailUtils emailUtils) {
        this.repository = repository;
        this.emailUtils = emailUtils;
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
        emailUtils.notifyTokenOwner(tokenEntity);
        return toDto(tokenEntity);
    }

    public TokenInfoDto getTokenInfo(String token) {

        TokenEntity tokenEntity = repository.getByTokenValue(token);
        return toDto(tokenEntity);
    }

    public TokenEntity getTokenByTokenValue(String token) {
        return repository.getByTokenValue(token);
    }

    public boolean existByTokenValue(String token) {

        boolean result = repository.existsByTokenValue(token);
        if (!result) logger.warn(String.format(LOG_TOKEN_NOT_EXISTING, token));
        return result;
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