package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.entities.Client;
import co.com.bancolombia.model.gateways.ClientRepository;
import co.com.bancolombia.r2dbc.entities.ClientData;
import co.com.bancolombia.r2dbc.repositories.ClientDataRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
@RequiredArgsConstructor
public class ClientDataRepositoryAdapter implements ClientRepository {

    private final ClientDataRepository repository;

    @Override
    public Mono<Client> findActiveByClientId(UUID clientId) {
        return repository.findByClientIdAndActiveTrue(clientId)
                .map(this::toModel);
    }

    private Client toModel(ClientData data) {
        return new Client(data.getClientId(), data.getClientSecret(), data.getScopes(),
                data.isActive());
    }
}
