package co.com.bancolombia.api.security.service;

import co.com.bancolombia.usecase.GetUserByUsernameUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements ReactiveUserDetailsService {

    private final GetUserByUsernameUseCase getUserByUsernameUseCase;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return getUserByUsernameUseCase.execute(username)
                .switchIfEmpty(
                        Mono.error(new UsernameNotFoundException("User not found: " + username)))
                .flatMap(SecurityUserDetails::build);
    }
}
