package pl.pelipe.emailmicroservice.token;

import java.time.LocalDateTime;
import java.util.Objects;

public class TokenInfoDto {

    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime validUntil;

    private LocalDateTime lastUsed;

    private Long dailyUsageCounter;

    private Long dailyUsageLimit;

    public TokenInfoDto() {
    }

    public TokenInfoDto(LocalDateTime createdAt, LocalDateTime validUntil, LocalDateTime lastUsed, Long dailyUsageCounter, Long dailyUsageLimit, Boolean isActive) {
        this.createdAt = createdAt;
        this.validUntil = validUntil;
        this.lastUsed = lastUsed;
        this.dailyUsageCounter = dailyUsageCounter;
        this.dailyUsageLimit = dailyUsageLimit;
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDateTime validUntil) {
        this.validUntil = validUntil;
    }

    public LocalDateTime getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(LocalDateTime lastUsed) {
        this.lastUsed = lastUsed;
    }

    public Long getDailyUsageCounter() {
        return dailyUsageCounter;
    }

    public void setDailyUsageCounter(Long dailyUsageCounter) {
        this.dailyUsageCounter = dailyUsageCounter;
    }

    public Long getDailyUsageLimit() {
        return dailyUsageLimit;
    }

    public void setDailyUsageLimit(Long dailyUsageLimit) {
        this.dailyUsageLimit = dailyUsageLimit;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenInfoDto that = (TokenInfoDto) o;
        return Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(validUntil, that.validUntil) &&
                Objects.equals(lastUsed, that.lastUsed) &&
                Objects.equals(dailyUsageCounter, that.dailyUsageCounter) &&
                Objects.equals(dailyUsageLimit, that.dailyUsageLimit) &&
                Objects.equals(isActive, that.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdAt, validUntil, lastUsed, dailyUsageCounter, dailyUsageLimit, isActive);
    }

    @Override
    public String toString() {
        return "TokenInfoDto{" +
                "createdAt=" + createdAt +
                ", validUntil=" + validUntil +
                ", lastUsed=" + lastUsed +
                ", dailyUsageCounter=" + dailyUsageCounter +
                ", dailyUsageLimit=" + dailyUsageLimit +
                ", isActive=" + isActive +
                '}';
    }
}
