package co.com.bancolombia.r2dbc.repositories;

import co.com.bancolombia.r2dbc.entities.UserData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserDataRepository extends ReactiveCrudRepository<UserData, String> {

    Mono<UserData> findByUsername(String username);

    Mono<Boolean> existsByUsername(String username);

    Mono<Boolean> existsByEmail(String email);
}
