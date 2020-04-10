package pl.pelipe.emailmicroservice.email;

import lombok.Data;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "email")
@Data
public class EmailArchiveEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String fromAddress;

    private String fromName;

    private String toAddress;

    private String subject;

    @Column(length = 10000)
    private String content;

    private EmailStatus status;

    private String provider;

    private Integer sendRetry;

    private Integer providerResponse;

    private LocalDateTime createdAt;

    private LocalDateTime lastUpdate;

    private LocalDateTime successSent;
}