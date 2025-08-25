package co.com.bancolombia.api.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

import co.com.bancolombia.api.handler.AuthHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterRest {

    @Bean
    public RouterFunction<ServerResponse> authRoutes(AuthHandler handler) {
        return RouterFunctions.route(POST("/auth/signin"), handler::authenticateUser)
                .andRoute(POST("/auth/signup"), handler::registerUser)
                .andRoute(POST("/auth/refreshtoken"), handler::refreshToken)
                .andRoute(POST("/auth/logout"), handler::logoutUser);
    }
}
