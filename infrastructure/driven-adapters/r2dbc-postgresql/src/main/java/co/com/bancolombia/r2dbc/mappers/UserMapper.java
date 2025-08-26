package co.com.bancolombia.r2dbc.mappers;


import co.com.bancolombia.model.entities.User;
import co.com.bancolombia.r2dbc.entities.UserData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    User toModel(UserData data);
    UserData toData(User model);
}
