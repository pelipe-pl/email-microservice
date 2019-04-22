package pl.pelipe.emailmicroservice.token;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tokenValue;

    private String owner;

    private LocalDateTime createdAt;

    private LocalDateTime validUntil;

    private LocalDateTime lastUsed;

    private Long dailyUsageCounter;

    private Long dailyUsageLimit;

    private Boolean isActive;

    public TokenEntity() {
    }

    public TokenEntity(String tokenValue, String owner, LocalDateTime createdAt, LocalDateTime validUntil, LocalDateTime lastUsed, Long dailyUsageCounter, Long dailyUsageLimit, Boolean isActive) {
        this.tokenValue = tokenValue;
        this.owner = owner;
        this.createdAt = createdAt;
        this.validUntil = validUntil;
        this.lastUsed = lastUsed;
        this.dailyUsageCounter = dailyUsageCounter;
        this.dailyUsageLimit = dailyUsageLimit;
        this.isActive = isActive;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenEntity that = (TokenEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(tokenValue, that.tokenValue) &&
                Objects.equals(owner, that.owner) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(validUntil, that.validUntil) &&
                Objects.equals(lastUsed, that.lastUsed) &&
                Objects.equals(dailyUsageCounter, that.dailyUsageCounter) &&
                Objects.equals(dailyUsageLimit, that.dailyUsageLimit) &&
                Objects.equals(isActive, that.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tokenValue, owner, createdAt, validUntil, lastUsed, dailyUsageCounter, dailyUsageLimit, isActive);
    }

    @Override
    public String toString() {
        return "TokenEntity{" +
                "id=" + id +
                ", tokenValue='" + tokenValue + '\'' +
                ", owner='" + owner + '\'' +
                ", createdAt=" + createdAt +
                ", validUntil=" + validUntil +
                ", lastUsed=" + lastUsed +
                ", dailyUsageCounter=" + dailyUsageCounter +
                ", dailyUsageLimit=" + dailyUsageLimit +
                ", isActive=" + isActive +
                '}';
    }
}

