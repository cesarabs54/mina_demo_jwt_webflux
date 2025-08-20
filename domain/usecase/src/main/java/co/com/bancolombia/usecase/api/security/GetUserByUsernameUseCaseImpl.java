package co.com.bancolombia.usecase.api.security;

import co.com.bancolombia.model.entities.User;
import co.com.bancolombia.model.gateways.UserRepository;
import co.com.bancolombia.usecase.GetUserByUsernameUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetUserByUsernameUseCaseImpl implements GetUserByUsernameUseCase {

    private final UserRepository userRepository;

    @Override
    public Mono<User> execute(String username) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(
                        new RuntimeException(
                                "Error: Username not found - " + username)));
    }


}