package pl.pelipe.emailmicroservice.token;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenService {

    private final TokenRepository repository;

    public TokenService(TokenRepository repository) {
        this.repository = repository;
    }

    public int validate(String token) {
        TokenEntity tokenEntity = repository.getByTokenValue(token);
        if (isValid(tokenEntity)) return 0;
        if (!isValid(tokenEntity)) return 1;
        if (isUsageLimitNotExceeded(tokenEntity)) return 2;
        return 1;
    }

    private Boolean isValid(TokenEntity tokenEntity) {
        return tokenEntity != null &&
                tokenEntity.getActive() &&
                !tokenEntity.getValidUntil().isBefore(LocalDateTime.now());
    }

    private Boolean isUsageLimitNotExceeded(TokenEntity tokenEntity) {

        Long dailyUsageCounter = tokenEntity.getDailyUsageCounter();
        Long dailyUsageLimit = tokenEntity.getDailyUsageLimit();
        LocalDateTime lastUsed = tokenEntity.getLastUsed();

        if (lastUsed.isBefore(LocalDateTime.now().minusDays(1))) {
            tokenEntity.setLastUsed(LocalDateTime.now());
            tokenEntity.setDailyUsageCounter(0L);
            repository.save(tokenEntity);
            return true;
        } else if (!lastUsed.isBefore(LocalDateTime.now().minusDays(1)) || dailyUsageLimit > dailyUsageCounter) {
            tokenEntity.setLastUsed(LocalDateTime.now());
            tokenEntity.setDailyUsageCounter(dailyUsageCounter + 1);
            repository.save(tokenEntity);
            return true;
        } else return false;
    }
}