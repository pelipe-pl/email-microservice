package pl.pelipe.emailmicroservice.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.pelipe.emailmicroservice.dashboard.TokenStatsScheduledService;

import java.util.List;

@Service
public class EmailResendScheduledService {

    private final EmailArchiveRepository emailArchiveRepository;
    private final SendEmailService sendEmailService;
    private final Logger logger = LoggerFactory.getLogger(TokenStatsScheduledService.class);

    public EmailResendScheduledService(EmailArchiveRepository emailArchiveRepository, SendEmailService sendEmailService) {
        this.emailArchiveRepository = emailArchiveRepository;
        this.sendEmailService = sendEmailService;
    }

    @Scheduled(fixedRate = 1800000)
    private void resendAllPending() {
        List<EmailArchiveEntity> pendingEmails = emailArchiveRepository.getAllByStatusAndSendRetryLessThan(EmailStatus.PENDING, 3);
        if (pendingEmails.isEmpty()) {
            logger.debug("No pending emails for resend.");
            return;
        }
        logger.info("Found " + pendingEmails.size() + " pending emails. Trying to resend...");
        int successCounter = 0;
        int failureCounter = 0;
        int processedCounter = 0;
        for (EmailArchiveEntity email : pendingEmails) {
            boolean result = sendEmailService.resend(email);
            processedCounter++;
            if (result) successCounter++;
            else failureCounter++;
        }
        logger.info("Resend all pending emails service finished.");
        logger.info("Succeeded: " + successCounter + ", failed: " + failureCounter + ", processed " + processedCounter);
    }
}