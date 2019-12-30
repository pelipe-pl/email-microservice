package pl.pelipe.emailmicroservice.token;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TokenInfoDto {

    private String ownerEmail;

    private String value;

    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime validUntil;

    private LocalDateTime lastUsed;

    private Long dailyUsageCounter;

    private Long dailyUsageLimit;
}