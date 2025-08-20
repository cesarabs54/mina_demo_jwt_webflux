package co.com.bancolombia.usecase.api.security;

import co.com.bancolombia.model.dtos.AuthRequest;
import co.com.bancolombia.model.dtos.AuthResponse;
import co.com.bancolombia.model.gateways.JwtGateway;
import co.com.bancolombia.model.gateways.PasswordEncoderService;
import co.com.bancolombia.model.gateways.UserRepository;
import co.com.bancolombia.usecase.AuthenticateUserUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class AuthenticateUserUseCaseImpl implements AuthenticateUserUseCase {

    private final UserRepository userRepository;
    private final JwtGateway jwtGateway;
    private final PasswordEncoderService passwordEncoderService;

    @Override
    public Mono<AuthResponse> execute(AuthRequest request) {
        return userRepository.findByUsername(request.username())
                .switchIfEmpty(Mono.error(
                        new RuntimeException(
                                "Error: Username not found - " + request.username()))
                )
                .flatMap(user -> passwordEncoderService.matches(request.password(),
                                user.getPassword())
                        .flatMap(matches -> {
                            if (!matches) {
                                return Mono.error(new RuntimeException(
                                        "Error: Username not found - " + request.username()));
                            }
                            return jwtGateway.generateAccessToken(user)
                                    .zipWith(jwtGateway.generateRefreshToken(user))
                                    .map(tokens -> new AuthResponse(
                                            tokens.getT1(),
                                            tokens.getT2(),
                                            user.getId(),
                                            user.getUsername(),
                                            user.getName(),
                                            user.getEmail(),
                                            user.getRolesAsString()
                                    ));
                        })
                );
    }

}
