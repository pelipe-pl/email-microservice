package pl.pelipe.emailmicroservice.dashboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.pelipe.emailmicroservice.token.TokenRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static pl.pelipe.emailmicroservice.config.Keys.LOG_SCHEDULER_TOKEN_STATS_UPDATE;

@Service
public class TokenStatsScheduledService {

    private TokenRepository tokenRepository;
    private Map<String, Long> tokenStats = new HashMap<>();

    private Logger logger = LoggerFactory.getLogger(TokenStatsScheduledService.class);

    public TokenStatsScheduledService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Scheduled(fixedRate = 60000)
    private void statsMonitor() {
        Long totalTokens = tokenRepository.count();
        Long activeTokens = tokenRepository.countAllByIsActiveOrValidUntilAfter(true, LocalDateTime.now());
        Long newTokensLast7days = tokenRepository.countAllByCreatedAtAfter(LocalDateTime.now().minusDays(7L));

        tokenStats.put("totalTokens", totalTokens);
        tokenStats.put("activeTokens", activeTokens);
        tokenStats.put("newTokensLast7days", newTokensLast7days);

        logger.debug(LOG_SCHEDULER_TOKEN_STATS_UPDATE);
    }

    public Map<String, Long> getTokenStats() {
        return tokenStats;
    }
}
