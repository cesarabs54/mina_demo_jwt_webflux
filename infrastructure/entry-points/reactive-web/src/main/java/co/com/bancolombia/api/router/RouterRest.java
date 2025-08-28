package co.com.bancolombia.api.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

import co.com.bancolombia.api.dto.requests.LogOutRequest;
import co.com.bancolombia.api.dto.requests.LoginRequest;
import co.com.bancolombia.api.dto.requests.SignUpRequest;
import co.com.bancolombia.api.dto.requests.TokenRefreshRequest;
import co.com.bancolombia.api.dto.responses.JwtResponse;
import co.com.bancolombia.api.dto.responses.MessageResponse;
import co.com.bancolombia.api.dto.responses.TokenRefreshResponse;
import co.com.bancolombia.api.handler.AuthHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterRest {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/auth/signin",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = {org.springframework.web.bind.annotation.RequestMethod.POST},
                    beanClass = AuthHandler.class,
                    beanMethod = "authenticateUser",
                    operation = @Operation(
                            summary = "Autenticar usuario",
                            description = "Genera un JWT a partir de credenciales válidas",
                            tags = {"Auth API"},
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = LoginRequest.class))
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "OK",
                                            content = @Content(schema = @Schema(implementation = JwtResponse.class))),
                                    @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
                                    @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/auth/signup",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = {org.springframework.web.bind.annotation.RequestMethod.POST},
                    beanClass = AuthHandler.class,
                    beanMethod = "registerUser",
                    operation = @Operation(
                            summary = "Registrar usuario",
                            description = "Registra un nuevo usuario en el sistema",
                            tags = {"Auth API"},
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = SignUpRequest.class))
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Usuario creado correctamente",
                                            content = @Content(schema = @Schema(implementation = MessageResponse.class))),
                                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/auth/refreshtoken",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = {org.springframework.web.bind.annotation.RequestMethod.POST},
                    beanClass = AuthHandler.class,
                    beanMethod = "refreshToken",
                    operation = @Operation(
                            summary = "Refrescar token JWT",
                            description = "Genera un nuevo access token a partir del refresh token",
                            tags = {"Auth API"},
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = TokenRefreshRequest.class))
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Token renovado",
                                            content = @Content(schema = @Schema(implementation = TokenRefreshResponse.class))),
                                    @ApiResponse(responseCode = "403", description = "Refresh token inválido o expirado")
                            }
                    )
            ),
            @RouterOperation(
                    path = "/auth/logout",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = {org.springframework.web.bind.annotation.RequestMethod.POST},
                    beanClass = AuthHandler.class,
                    beanMethod = "logoutUser",
                    operation = @Operation(
                            summary = "Cerrar sesión",
                            description = "Elimina el refresh token del usuario",
                            tags = {"Auth API"},
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(schema = @Schema(implementation = LogOutRequest.class))
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Log out successful",
                                            content = @Content(schema = @Schema(implementation = MessageResponse.class)))
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> authRoutes(AuthHandler handler) {
        return RouterFunctions
                .route(POST("/auth/signin"), handler::authenticateUser)
                .andRoute(POST("/auth/signup"), handler::registerUser)
                .andRoute(POST("/auth/refreshtoken"), handler::refreshToken)
                .andRoute(POST("/auth/logout"), handler::logoutUser);
    }
}
