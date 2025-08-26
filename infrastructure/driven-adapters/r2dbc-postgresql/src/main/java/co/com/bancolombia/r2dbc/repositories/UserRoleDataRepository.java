package co.com.bancolombia.r2dbc.repositories;

import co.com.bancolombia.r2dbc.entities.UserRoleData;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleDataRepository extends ReactiveCrudRepository<UserRoleData, Void> {

}

