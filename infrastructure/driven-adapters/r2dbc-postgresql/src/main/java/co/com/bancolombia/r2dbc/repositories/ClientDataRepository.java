package co.com.bancolombia.r2dbc.repositories;

import co.com.bancolombia.r2dbc.entities.ClientData;
import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ClientDataRepository extends ReactiveCrudRepository<ClientData, UUID> {

    Mono<ClientData> findByClientIdAndActiveTrue(UUID clientId);
}
