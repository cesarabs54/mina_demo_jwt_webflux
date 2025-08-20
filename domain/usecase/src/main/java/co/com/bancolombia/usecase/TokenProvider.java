package co.com.bancolombia.usecase;

import reactor.core.publisher.Mono;

public interface TokenProvider {

    Mono<Boolean> validateToken(String token);
    Mono<String> getUsernameFromToken(String token);
     // para logout
}

