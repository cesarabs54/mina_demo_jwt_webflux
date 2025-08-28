package co.com.bancolombia.r2dbc.repositories;

import co.com.bancolombia.r2dbc.entities.ERoleData;
import co.com.bancolombia.r2dbc.entities.RoleData;
import java.util.Collection;
import java.util.UUID;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoleDataRepository extends R2dbcRepository<RoleData, UUID> {

    Mono<RoleData> findByName(ERoleData name);

    Flux<RoleData> findAllByNameIn(Collection<ERoleData> names);


}
