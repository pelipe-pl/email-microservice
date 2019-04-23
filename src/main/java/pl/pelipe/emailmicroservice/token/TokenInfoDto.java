package pl.pelipe.emailmicroservice.token;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TokenInfoDto {

    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime validUntil;

    private LocalDateTime lastUsed;

    private Long dailyUsageCounter;

    private Long dailyUsageLimit;
}