package co.com.bancolombia.usecase.api.security;

import co.com.bancolombia.model.dtos.RegisterRequest;
import co.com.bancolombia.model.entities.Role;
import co.com.bancolombia.model.entities.User;
import co.com.bancolombia.model.enums.ERole;
import co.com.bancolombia.model.gateways.PasswordEncoderService;
import co.com.bancolombia.model.gateways.RoleRepository;
import co.com.bancolombia.model.gateways.UserRepository;
import co.com.bancolombia.usecase.RegisterUserUseCase;
import java.util.HashSet;
import java.util.Set;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RegisterUserUseCaseImpl implements RegisterUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoderService passwordEncoderService;

    public RegisterUserUseCaseImpl(UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoderService passwordEncoderService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoderService = passwordEncoderService;
    }

    @Override
    public Mono<User> execute(RegisterRequest request) {
        return userRepository.existsByUsername(request.username())
                .flatMap(existsUsername -> {
                    if (existsUsername) {
                        return Mono.error(
                                new IllegalArgumentException("El nombre de usuario ya existe"));
                    }
                    return userRepository.existsByEmail(request.email());
                })
                .flatMap(existsEmail -> {
                    if (existsEmail) {
                        return Mono.error(new IllegalArgumentException("El correo ya está en uso"));
                    }

                    Set<String> strRoles = request.roles();
                    Flux<Role> rolesFlux;

                    if (strRoles == null || strRoles.isEmpty()) {
                        rolesFlux = roleRepository.findByName(ERole.ROLE_USER)
                                .switchIfEmpty(Mono.error(new IllegalStateException(
                                        "No se encontró el rol ROLE_USER")))
                                .flux();
                    } else {
                        rolesFlux = Flux.fromIterable(strRoles)
                                .flatMap(role -> {
                                    ERole eRole = ERole.valueOf(role);
                                    return roleRepository.findByName(eRole)
                                            .switchIfEmpty(Mono.error(new IllegalStateException(
                                                    "Rol no encontrado: " + role)));
                                });
                    }

                    return rolesFlux.collectList()
                            .flatMap(roles ->
                                    passwordEncoderService.encode(request.password())
                                            .flatMap(encodedPassword -> {
                                                User newUser = new User(
                                                        null,
                                                        request.username(),
                                                        request.name(),
                                                        request.email(),
                                                        encodedPassword,
                                                        new HashSet<>(roles)
                                                );
                                                return userRepository.save(newUser);
                                            })
                            );
                });
    }

}


