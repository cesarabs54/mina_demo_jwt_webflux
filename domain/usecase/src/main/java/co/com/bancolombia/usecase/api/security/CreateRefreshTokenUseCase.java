package co.com.bancolombia.usecase.api.security;


import co.com.bancolombia.model.entities.RefreshToken;
import co.com.bancolombia.model.gateways.RefreshTokenRepository;
import co.com.bancolombia.model.gateways.UserRepository;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateRefreshTokenUseCase {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public Mono<RefreshToken> execute(String username, String token, Instant expiry) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(
                        new RuntimeException(
                                "Error: Username not found - " + username))
                ).flatMap(
                        userFound -> refreshTokenRepository.save(RefreshToken.builder()
                                .user(userFound)
                                .token(token)
                                .expiryDate(expiry)
                                .build())

                );
    }
}
