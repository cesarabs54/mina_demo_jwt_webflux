package co.com.bancolombia.usecase.api.security;

import co.com.bancolombia.model.dtos.RegisterCommand;
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

    public RegisterUserUseCaseImpl(UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoderService passwordEncoderService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoderService = passwordEncoderService;
    }

    @Override
    public Mono<User> execute(RegisterCommand request) {
        return userRepository.existsByUsername(request.username())
                .flatMap(existsUsername -> {
                    if (Boolean.TRUE.equals(existsUsername)) {
                        return Mono.error(
                                new IllegalArgumentException("El nombre de usuario ya existe"));
                    }
                    return userRepository.existsByEmail(request.email());
                })
                .flatMap(existsEmail -> {
                    if (Boolean.TRUE.equals(existsEmail)) {
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
                                .flatMap(
                                        roleStr -> roleRepository.findByName(ERole.valueOf(roleStr))
                                                .switchIfEmpty(Mono.error(new IllegalStateException(
                                                        "Rol no encontrado: " + roleStr))));
                    }

                    return rolesFlux.collectList()
                            .flatMap(roles -> passwordEncoderService.encode(request.password())
                                    .map(encodedPassword -> new User(
                                            null,
                                            request.username(),
                                            request.name(),
                                            request.email(),
                                            encodedPassword,
                                            new HashSet<>(roles)
                                    )))
                            .flatMap(userRepository::save); // ✅ AGREGAR ESTA LÍNEA CRUCIAL
                });
    }
}
