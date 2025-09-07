package co.com.bancolombia.model.gateways;

import co.com.bancolombia.model.entities.Client;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface ClientRepository {

    Mono<Client> findActiveByClientId(UUID clientId);
}
