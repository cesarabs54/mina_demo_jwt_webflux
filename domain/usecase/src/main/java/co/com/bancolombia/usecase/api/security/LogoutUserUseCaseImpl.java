package co.com.bancolombia.usecase.api.security;


import co.com.bancolombia.model.gateways.JwtGateway;
import co.com.bancolombia.usecase.LogoutUserUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LogoutUserUseCaseImpl implements LogoutUserUseCase {

    private final JwtGateway jwtGateway;

    @Override
    public Mono<Void> execute(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("userId cannot be null or blank");
        }
        return jwtGateway.invalidateRefreshTokensForUser(username);
    }
}

