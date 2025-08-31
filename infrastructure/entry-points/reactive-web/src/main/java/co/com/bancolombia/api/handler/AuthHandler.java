package co.com.bancolombia.api.handler;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import co.com.bancolombia.api.dto.requests.LogOutRequest;
import co.com.bancolombia.api.dto.requests.LoginRequest;
import co.com.bancolombia.api.dto.requests.SignUpRequest;
import co.com.bancolombia.api.dto.requests.TokenRefreshRequest;
import co.com.bancolombia.api.dto.responses.JwtResponse;
import co.com.bancolombia.api.dto.responses.MessageResponse;
import co.com.bancolombia.api.exception.TokenRefreshException;
import co.com.bancolombia.api.util.RequestValidator;
import co.com.bancolombia.model.dtos.AuthRequest;
import co.com.bancolombia.model.dtos.RefreshAccessTokenRequest;
import co.com.bancolombia.model.dtos.RegisterCommand;
import co.com.bancolombia.usecase.AuthenticateUserUseCase;
import co.com.bancolombia.usecase.LogoutUserUseCase;
import co.com.bancolombia.usecase.RegisterUserUseCase;
import co.com.bancolombia.usecase.api.security.RefreshAccessTokenUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthHandler {

    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final RequestValidator requestValidator;
    private final RegisterUserUseCase registerUserUseCase;
    private final RefreshAccessTokenUseCase refreshAccessTokenUseCase;
    private final LogoutUserUseCase logoutUserUseCase;
    private final ObjectMapper objectMapper;

    public Mono<ServerResponse> authenticateUser(ServerRequest request) {
        return request.bodyToMono(LoginRequest.class)
                .flatMap(requestValidator::validate)
                .map(loginRequest -> objectMapper.convertValue(loginRequest, AuthRequest.class))
                .flatMap(authenticateUserUseCase::execute) // <-- Ejecuta el caso de uso
                .map(authResponse -> objectMapper.convertValue(authResponse, JwtResponse.class))
                .flatMap(jwtResponse -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(jwtResponse));
    }


    public Mono<ServerResponse> registerUser(ServerRequest request) {
        return request.bodyToMono(SignUpRequest.class)
                .flatMap(requestValidator::validate)
                .map(signUpRequest -> objectMapper.convertValue(signUpRequest,
                        RegisterCommand.class))
                .flatMap(registerUserUseCase::execute)
                .flatMap(user -> ServerResponse.ok() // ✅ Cambiar .then() por .flatMap()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(new MessageResponse("Usuario creado correctamente")));
    }

    public Mono<ServerResponse> refreshToken(ServerRequest request) {
        return request.bodyToMono(TokenRefreshRequest.class)
                .flatMap(requestValidator::validate)
                .flatMap(req -> {
                    var domainRequest = objectMapper.convertValue(req,
                            RefreshAccessTokenRequest.class);
                    return Mono.justOrEmpty(refreshAccessTokenUseCase.execute(domainRequest))
                            .switchIfEmpty(Mono.error(new TokenRefreshException(req.refreshToken(),
                                    "Refresh token no está en la base de datos!")));
                })
                .flatMap(token -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(token));
    }

    public Mono<ServerResponse> logoutUser(ServerRequest request) {
        return request.bodyToMono(LogOutRequest.class)
                .flatMap(requestValidator::validate)
                .flatMap(req -> logoutUserUseCase.execute(
                        req.userId())) // Cambiado de doOnNext a flatMap
                .then(ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(new MessageResponse("Log out successful!")));
    }
}