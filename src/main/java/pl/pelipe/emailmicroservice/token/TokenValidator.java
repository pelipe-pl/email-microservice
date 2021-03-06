package pl.pelipe.emailmicroservice.token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static pl.pelipe.emailmicroservice.config.keys.Keys.*;

@Service
public class TokenValidator {

    private final TokenRepository tokenRepository;
    private Logger logger = LoggerFactory.getLogger(TokenValidator.class);

    public TokenValidator(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public boolean validate(String token) {

        TokenEntity tokenEntity = tokenRepository.getByTokenValue(token);

        if (isValid(tokenEntity) && !isUsageLimitExceeded(tokenEntity)) {
            updateTokenData(tokenEntity);
            logger.info(String.format(LOG_TOKEN_USAGE_INFO, tokenEntity.getId()));
            return true;
        } else {
            logger.warn(String.format(LOG_TOKEN_INVALID, token));
            return false;
        }
    }

    private boolean isValid(TokenEntity tokenEntity) {

        return tokenEntity != null &&
                tokenEntity.getIsActive() &&
                tokenEntity.getValidUntil().isAfter(LocalDateTime.now());
    }

    private boolean isUsageLimitExceeded(TokenEntity tokenEntity) {

        Long dailyUsageCounter = tokenEntity.getDailyUsageCounter();
        Long dailyUsageLimit = tokenEntity.getDailyUsageLimit();
        LocalDateTime lastUsed = tokenEntity.getLastUsed();

        if (lastUsed == null || lastUsed.isBefore(LocalDateTime.now().minusHours(24))) return false;
        else if (lastUsed.isAfter(LocalDateTime.now().minusHours(24)) && dailyUsageLimit <= dailyUsageCounter) {
            logger.warn(String.format(LOG_TOKEN_USAGE_LIMIT_REACHED, tokenEntity.getDailyUsageLimit(), tokenEntity.getId()));
            return true;
        }
        return false;
    }

    private void updateTokenData(TokenEntity tokenEntity) {

        Long dailyUsageCounter = tokenEntity.getDailyUsageCounter();
        LocalDateTime lastUsed = tokenEntity.getLastUsed();

        if (lastUsed != null) {
            if (lastUsed.isBefore(LocalDateTime.now().minusHours(24))) {
                tokenEntity.setDailyUsageCounter(1L);
            }
            if (!lastUsed.isBefore(LocalDateTime.now().minusHours(24))) {
                tokenEntity.setDailyUsageCounter(dailyUsageCounter + 1L);
            }
        }
        tokenEntity.setLastUsed(LocalDateTime.now());
        tokenRepository.save(tokenEntity);
    }
}