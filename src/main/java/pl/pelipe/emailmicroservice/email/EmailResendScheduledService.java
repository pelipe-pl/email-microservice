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

    @Scheduled(fixedRate = 60000)
    private void resendAllPending() {
        logger.info("ResendAllPending service starts.");
        List<EmailArchiveEntity> pendingEmails = emailArchiveRepository.getAllByStatusAndSendRetryLessThan(EmailStatus.PENDING, 3);
        logger.info("ResendAllPending service has " + pendingEmails.size() + " in pending status.");
        for (EmailArchiveEntity email : pendingEmails) {
            sendEmailService.resend(email);
        }
        logger.info("ResendAllPending email service finished.");
    }
}

