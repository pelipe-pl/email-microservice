package pl.pelipe.emailmicroservice.token;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenService {

    private final TokenRepository repository;

    public TokenService(TokenRepository repository) {
        this.repository = repository;
    }

    public boolean validate(String token) {
        TokenEntity tokenEntity = repository.getByTokenValue(token);
        if (isValid(tokenEntity) && !isUsageLimitExceeded(tokenEntity) ) {
            updateTokenData(tokenEntity);
            return true;
        } else return false;
    }

    public TokenInfoDto getTokenInfo(String token){
        TokenEntity tokenEntity = repository.getByTokenValue(token);
        TokenInfoDto tokenInfoDto = new TokenInfoDto();
        tokenInfoDto.setActive(tokenEntity.getActive());
        tokenInfoDto.setCreatedAt(tokenEntity.getCreatedAt());
        tokenInfoDto.setDailyUsageCounter(tokenEntity.getDailyUsageCounter());
        tokenInfoDto.setLastUsed(tokenEntity.getLastUsed());
        tokenInfoDto.setValidUntil(tokenEntity.getValidUntil());
        tokenInfoDto.setDailyUsageLimit(tokenEntity.getDailyUsageLimit());
        return tokenInfoDto;
    }

    public boolean existByTokenValue(String token){
        return repository.existsByTokenValue(token);
    }

    private boolean isValid(TokenEntity tokenEntity) {

        return tokenEntity != null &&
                tokenEntity.getActive() &&
                tokenEntity.getValidUntil().isAfter(LocalDateTime.now());
    }

    private boolean isUsageLimitExceeded(TokenEntity tokenEntity) {

        Long dailyUsageCounter = tokenEntity.getDailyUsageCounter();
        Long dailyUsageLimit = tokenEntity.getDailyUsageLimit();
        LocalDateTime lastUsed = tokenEntity.getLastUsed();

        if (lastUsed.isBefore(LocalDateTime.now().minusHours(24))) return false;
        else if (lastUsed.isAfter(LocalDateTime.now().minusHours(24)) && dailyUsageLimit <= dailyUsageCounter)
            return true;
        return false;
    }

    private void updateTokenData(TokenEntity tokenEntity) {

        Long dailyUsageCounter = tokenEntity.getDailyUsageCounter();
        LocalDateTime lastUsed = tokenEntity.getLastUsed();

        if (lastUsed.isBefore(LocalDateTime.now().minusHours(24))) {
            tokenEntity.setDailyUsageCounter(1L);
        }
        if (!lastUsed.isBefore(LocalDateTime.now().minusHours(24))) {
            tokenEntity.setDailyUsageCounter(dailyUsageCounter + 1L);
        }
        tokenEntity.setLastUsed(LocalDateTime.now());
        repository.save(tokenEntity);
    }
}