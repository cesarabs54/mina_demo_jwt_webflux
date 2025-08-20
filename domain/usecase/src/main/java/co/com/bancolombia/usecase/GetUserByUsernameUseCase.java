package co.com.bancolombia.usecase;

import co.com.bancolombia.model.entities.User;
import reactor.core.publisher.Mono;


public interface GetUserByUsernameUseCase {

    Mono<User> execute(String username);
}
