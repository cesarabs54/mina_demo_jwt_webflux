package co.com.bancolombia.r2dbc.adapter;

import static co.com.bancolombia.r2dbc.util.IdGeneratorUtil.generateDefaultUUID;

import co.com.bancolombia.model.entities.Role;
import co.com.bancolombia.model.entities.User;
import co.com.bancolombia.model.gateways.UserRepository;
import co.com.bancolombia.r2dbc.entities.UserData;
import co.com.bancolombia.r2dbc.entities.UserRoleData;
import co.com.bancolombia.r2dbc.mappers.UserMapper;
import co.com.bancolombia.r2dbc.repositories.UserDataRepository;
import co.com.bancolombia.r2dbc.repositories.UserRoleDataRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserDataRepositoryAdapter implements UserRepository {

    private final UserDataRepository repository;
    private final UserRoleDataRepository userRoleDataRepository;
    private final UserMapper mapper;

    @Override
    @Transactional
    public Mono<User> findByUsername(String username) {
        return repository.findByUsername(username).map(mapper::toModel);
    }

    @Override
    @Transactional
    public Mono<User> save(User user) {
        log.debug("Iniciando guardado de usuario: {}", user.getUsername());

        Set<Role> roles = user.getRoles();
        if (roles == null || roles.isEmpty()) {
            return Mono.error(
                    new IllegalArgumentException("El usuario debe tener al menos un rol"));
        }

        if (user.getUserId() != null) {
            log.debug("Usuario existente, actualizando...");
            return repository.save(mapper.toData(user)).map(mapper::toModel);
        }

        user.setUserId(generateDefaultUUID());
        log.debug("Usuario nuevo con ID: {}", user.getUserId());

        UserData userData = mapper.toData(user);
        userData.setNew(true);

        return repository.save(userData)
                .doOnNext(savedUser -> log.debug("Usuario guardado: {}", savedUser.getUserId()))
                .doOnError(error -> log.error("Error guardando usuario: ", error))
                .flatMap(savedUserData ->
                        Flux.fromIterable(roles)
                                .doOnNext(role -> log.debug("Guardando rol: {}", role.getRoleId()))
                                .flatMap(role -> {
                                    UserRoleData userRoleData = new UserRoleData(
                                            savedUserData.getUserId(),
                                            role.getRoleId()
                                    );
                                    return userRoleDataRepository.save(userRoleData)
                                            .doOnNext(saved -> log.debug("Rol guardado: {}", saved))
                                            .doOnError(error -> log.error("Error guardando rol: ",
                                                    error));
                                })
                                .then(Mono.just(savedUserData))
                )
                .map(mapper::toModel)
                .doOnSuccess(result -> log.debug("Proceso completado exitosamente"))
                .doOnError(error -> log.error("Error en el proceso completo: ", error));
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
