package pl.pelipe.emailmicroservice.dashboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.pelipe.emailmicroservice.user.UserRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static pl.pelipe.emailmicroservice.config.Keys.LOG_SCHEDULER_USER_STATS_UPDATE;

@Service
public class UserStatsScheduledService {

    private UserRepository userRepository;
    private Map<String, Long> userStats = new HashMap<>();
    private Logger logger = LoggerFactory.getLogger(UserStatsScheduledService.class);

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

        logger.info(LOG_SCHEDULER_USER_STATS_UPDATE);
    }

    public Map<String, Long> getUserStatsMap() {
        return userStats;
    }
}