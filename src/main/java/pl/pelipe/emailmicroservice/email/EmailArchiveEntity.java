package pl.pelipe.emailmicroservice.email;

import lombok.Data;
import pl.pelipe.emailmicroservice.token.TokenEntity;
import pl.pelipe.emailmicroservice.user.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "email")
@Data
public class EmailArchiveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "token_id")
    private TokenEntity token;

    private String fromAddress;

    private String fromName;

    private String toAddress;

    private String subject;

    @Column(length = 10000)
    private String content;

    private EmailStatus status;

    private String provider;

    private String providerId;

    private Integer sendRetry;

    private Integer providerResponse;

    private LocalDateTime createdAt;

    private LocalDateTime lastUpdate;

    private LocalDateTime successSent;
}