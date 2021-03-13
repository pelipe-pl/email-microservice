package pl.pelipe.emailmicroservice.token;

import org.springframework.stereotype.Component;

@Component
public class TokenUtils {
    private final TokenRepository tokenRepository;

    public TokenUtils(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }
    public TokenEntity getTokenByTokenValue(String token){
        return tokenRepository.getByTokenValue(token);
    }

    public TokenEntity getSystemTokenEntity(){
        return tokenRepository.getOne(0L);
    }
}
