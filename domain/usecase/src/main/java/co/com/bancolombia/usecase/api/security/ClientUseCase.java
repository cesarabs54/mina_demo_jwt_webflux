package co.com.bancolombia.usecase.api.security;

import co.com.bancolombia.model.entities.Client;
import co.com.bancolombia.model.entities.User;
import co.com.bancolombia.model.gateways.ClientRepository;
import co.com.bancolombia.model.gateways.JwtGateway;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ClientUseCase {

    private final ClientRepository clientRepository;

    private final JwtGateway jwtGateway;

    public Mono<Client> validateClientCredentials(UUID clientId, String clientSecret) {
        return clientRepository.findActiveByClientId(clientId)
                .filter(client -> client.getClientSecret().equals(clientSecret))
                .switchIfEmpty(Mono.error(new RuntimeException("Invalid client credentials")));
    }

    public Mono<Map<String, Object>> generateClientToken(Client client) {
        return jwtGateway.generateAccessToken(
                new User(null, client.getClientId().toString(), null, null, null, Set.of())
                // Usuario técnico
        ).map(accessToken -> Map.of(
                "access_token", accessToken,
                "token_type", "Bearer",
                "expires_in", 3600,
                "scope", client.getScopes()
        ));
    }

}
