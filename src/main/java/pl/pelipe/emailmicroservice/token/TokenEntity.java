package pl.pelipe.emailmicroservice.token;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tokenValue;

    @Email
    @NotNull
    @Size(min = 6)
    private String ownerEmail;

    private LocalDateTime createdAt;

    private LocalDateTime validUntil;

    private LocalDateTime lastUsed;

    @NotNull
    private Long dailyUsageCounter;

    @NotNull
    private Long dailyUsageLimit;

    private Boolean isActive;
}