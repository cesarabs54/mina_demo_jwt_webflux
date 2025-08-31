package co.com.bancolombia.usecase;


import co.com.bancolombia.model.dtos.RegisterCommand;
import co.com.bancolombia.model.entities.User;
import reactor.core.publisher.Mono;

public interface RegisterUserUseCase {

    Mono<User> execute(RegisterCommand request);
}
