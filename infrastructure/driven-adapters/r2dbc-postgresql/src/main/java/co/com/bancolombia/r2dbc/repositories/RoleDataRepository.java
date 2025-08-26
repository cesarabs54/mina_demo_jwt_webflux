package co.com.bancolombia.r2dbc.repositories;

import co.com.bancolombia.r2dbc.entities.ERoleData;
import co.com.bancolombia.r2dbc.entities.RoleData;
import java.util.Collection;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoleDataRepository extends ReactiveCrudRepository<RoleData, Integer> {

    Mono<RoleData> findByName(ERoleData name);

    Flux<RoleData> findAllByNameIn(Collection<ERoleData> names);


}
