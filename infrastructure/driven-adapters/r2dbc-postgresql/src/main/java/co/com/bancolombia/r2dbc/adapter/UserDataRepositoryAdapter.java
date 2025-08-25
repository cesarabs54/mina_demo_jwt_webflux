package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.entities.User;
import co.com.bancolombia.model.gateways.UserRepository;
import co.com.bancolombia.r2dbc.mappers.UserMapper;
import co.com.bancolombia.r2dbc.repositories.UserDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class UserDataRepositoryAdapter implements UserRepository {

    private final UserDataRepository repository;
    private final UserMapper mapper;

    @Override
    @Transactional
    public Mono<User> findByUsername(String username) {
        return repository.findByUsername(username).map(mapper::toModel);
    }

    @Override
    public Mono<User> save(User user) {
        return repository.save(mapper.toData(user)).map(mapper::toModel);
    }

    @Override
    public Mono<Boolean> existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
