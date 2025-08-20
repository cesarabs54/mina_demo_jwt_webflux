package co.com.bancolombia.model.gateways;

import co.com.bancolombia.model.entities.User;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<User> findByUsername(String username);

    Mono<User> save(User user);

    Mono<Boolean> existsByUsername(String username);

    Mono<Boolean> existsByEmail(String email);
}

