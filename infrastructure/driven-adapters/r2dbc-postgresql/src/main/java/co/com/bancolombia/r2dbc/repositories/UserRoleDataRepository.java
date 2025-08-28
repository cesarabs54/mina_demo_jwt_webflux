package co.com.bancolombia.r2dbc.repositories;

import co.com.bancolombia.r2dbc.entities.UserRoleData;
import java.util.UUID;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRoleDataRepository extends R2dbcRepository<UserRoleData, Void> {

    Flux<UserRoleData> findByUserId(UUID userId);
}

