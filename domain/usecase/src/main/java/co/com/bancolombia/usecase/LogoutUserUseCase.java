package co.com.bancolombia.usecase;


import reactor.core.publisher.Mono;

public interface LogoutUserUseCase {
    Mono<Void> execute(String userId);
}

