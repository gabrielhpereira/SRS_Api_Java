package com.api.srs.security.service.token;

import com.api.srs.security.entity.token.TokenEntity;
import com.api.srs.security.repository.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public List<TokenEntity> findAllValidTokenByUser(Integer userId) {
        return this.tokenRepository.findAllValidTokenByUser(userId);
    }

    public Optional<TokenEntity> findByToken(String token) {
        return this.tokenRepository.findByToken(token);
    }

    @Transactional
    public void saveToken(TokenEntity token) {
        this.tokenRepository.save(token);
    }

    @Transactional
    public void saveAllTokens(List<TokenEntity> validUserTokens) {
        this.tokenRepository.saveAll(validUserTokens);
    }
}
