package co.com.bancolombia.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import co.com.bancolombia.api.config.TestSecurityConfig;
import co.com.bancolombia.api.dto.requests.SignUpRequest;
import co.com.bancolombia.api.handler.AuthHandler;
import co.com.bancolombia.api.handler.GlobalErrorHandler;
import co.com.bancolombia.api.router.RouterRest;
import co.com.bancolombia.api.util.RequestValidator;
import co.com.bancolombia.model.entities.User;
import co.com.bancolombia.usecase.AuthenticateUserUseCase;
import co.com.bancolombia.usecase.LogoutUserUseCase;
import co.com.bancolombia.usecase.RegisterUserUseCase;
import co.com.bancolombia.usecase.api.security.RefreshAccessTokenUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@ContextConfiguration(classes = {
        RouterRest.class,
        AuthHandler.class,
        GlobalErrorHandler.class,
        TestSecurityConfig.class, // ✅ Configuración de seguridad para tests
})
@Import({ObjectMapper.class, RequestValidator.class}) // ✅ ObjectMapper real
@WebFluxTest
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private AuthenticateUserUseCase authenticateUserUseCase;
    @MockitoBean
    private RegisterUserUseCase registerUserUseCase;
    @MockitoBean
    private RefreshAccessTokenUseCase refreshAccessTokenUseCase;
    @MockitoBean
    private LogoutUserUseCase logoutUserUseCase;

    @Test
    void testSignupReturnsSuccessMessage() {
        var request = new SignUpRequest(
                "username",
                "name",
                "email@email.com",
                Set.of("ROLE_USER", "ROLE_ADMIN"),
                "password"
        );

        // 👉 Mock caso de uso: simula que creó el usuario
        when(registerUserUseCase.execute(any()))
                .thenReturn(Mono.just(new User())); // puede ser Mono.just("user-id") o User dummy

        webTestClient.post()
                .uri("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Usuario creado correctamente");
    }
}
