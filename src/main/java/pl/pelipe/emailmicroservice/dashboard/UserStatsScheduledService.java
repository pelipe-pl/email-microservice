package pl.pelipe.emailmicroservice.dashboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.pelipe.emailmicroservice.user.UserRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static pl.pelipe.emailmicroservice.config.keys.Keys.LOG_SCHEDULER_USER_STATS_UPDATE;

@Service
public class UserStatsScheduledService {

    private final UserRepository userRepository;
    private final Map<String, Long> userStats = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(UserStatsScheduledService.class);

    public UserStatsScheduledService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Scheduled(fixedRate = 60000)
    private void statsMonitor() {

        Long usersTotal = userRepository.count();
        Long usersActive = userRepository.countAllByIsActive(true);
        Long usersRegisteredLast7days = userRepository.countAllByCreatedAtAfter(LocalDateTime.now().minusDays(7));

        userStats.put("usersTotal", usersTotal);
        userStats.put("usersActive", usersActive);
        userStats.put("usersRegisteredLast7days", usersRegisteredLast7days);

        logger.debug(LOG_SCHEDULER_USER_STATS_UPDATE);
    }

    public Map<String, Long> getUserStatsMap() {
        return userStats;
    }
}