package co.com.bancolombia.r2dbc.mappers;

import co.com.bancolombia.model.entities.Role;
import co.com.bancolombia.r2dbc.entities.RoleData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toModel(RoleData data);

    @Mapping(target = "isNew", ignore = true)
    RoleData toData(Role model);
}
