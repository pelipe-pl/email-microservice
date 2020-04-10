package pl.pelipe.emailmicroservice.email;

import org.springframework.stereotype.Service;

@Service
public class EmailArchiveServiceImpl implements EmailArchiveService {

    private final EmailArchiveRepository emailArchiveRepository;

    public EmailArchiveServiceImpl(EmailArchiveRepository emailArchiveRepository) {
        this.emailArchiveRepository = emailArchiveRepository;
    }

    @Override
    public void save(EmailArchiveEntity emailArchiveEntity) {
         emailArchiveRepository.save(emailArchiveEntity);
    }
}
