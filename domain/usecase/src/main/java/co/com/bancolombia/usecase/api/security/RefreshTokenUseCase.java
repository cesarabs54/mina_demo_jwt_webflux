package co.com.bancolombia.usecase.api.security;


import co.com.bancolombia.model.entities.RefreshToken;
import co.com.bancolombia.model.gateways.RefreshTokenRepository;
import co.com.bancolombia.model.gateways.UserRepository;
import java.time.Instant;
import java.util.UUID;
import reactor.core.publisher.Mono;

public record RefreshTokenUseCase(RefreshTokenRepository refreshTokenRepository,
                                  UserRepository userRepository, long refreshTokenDurationMs) {

    public Mono<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public Mono<RefreshToken> createRefreshToken(String username) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(
                        new RuntimeException(
                                "Error: Username not found - " + username)))
                .flatMap(user1 -> refreshTokenRepository.save(RefreshToken.builder()
                        .user(user1)
                        .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                        .token(UUID.randomUUID().toString())
                        .build()));
    }

    public Mono<RefreshToken> verifyExpiration(RefreshToken token) {

        return Mono.defer(() -> {
            if (token.getExpiryDate().isBefore(Instant.now())) {
                return refreshTokenRepository.delete(token).then(
                        Mono.error(new IllegalArgumentException("Refresh token expired")));
            }
            return Mono.just(token);
        });
    }

    public Mono<Void> deleteByUsername(String username) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(
                        new RuntimeException(
                                "Error: Username not found - " + username)))
                .flatMap(user -> refreshTokenRepository.deleteByUser(user).then());
    }
}