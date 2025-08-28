package co.com.bancolombia.r2dbc.adapter;

import co.com.bancolombia.model.entities.Role;
import co.com.bancolombia.model.enums.ERole;
import co.com.bancolombia.model.gateways.RoleRepository;
import co.com.bancolombia.r2dbc.entities.ERoleData;
import co.com.bancolombia.r2dbc.mappers.RoleMapper;
import co.com.bancolombia.r2dbc.repositories.RoleDataRepository;
import co.com.bancolombia.r2dbc.repositories.UserRoleDataRepository;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RoleRepositoryAdapter implements RoleRepository {

    private final RoleDataRepository roleDataRepository;
    private final UserRoleDataRepository userRoleDataRepository;
    private final RoleMapper roleMapper;

    public Mono<Role> findByName(ERole name) {
        return roleDataRepository.findByName(ERoleData.valueOf(name.name()))
                .map(roleMapper::toModel);
    }

    @Override
    public Flux<Role> findByUserId(UUID userId) {
        return userRoleDataRepository.findByUserId(userId).flatMap(
                userRoleData -> roleDataRepository.findById(userRoleData.getRoleId())
                        .map(roleMapper::toModel)
        );
    }

    public Flux<Role> findByNames(Set<String> names) {

        Set<ERoleData> enumNames = names.stream()
                .map(ERoleData::valueOf)
                .collect(Collectors.toSet());

        return roleDataRepository.findAllByNameIn(enumNames)
                .map(roleMapper::toModel);
    }
}

