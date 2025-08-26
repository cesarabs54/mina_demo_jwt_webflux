package co.com.bancolombia.r2dbc.repositories;

import co.com.bancolombia.r2dbc.entities.RefreshTokenData;
import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface RefreshTokenDataRepository extends
        ReactiveCrudRepository<RefreshTokenData, String> {

    Mono<RefreshTokenData> findByToken(String token);

    Mono<Integer> deleteByUserId(UUID userId);
}
