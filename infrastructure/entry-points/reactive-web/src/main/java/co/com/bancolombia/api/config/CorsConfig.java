package co.com.bancolombia.api.config;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    // Lista de orígenes permitidos para CORS (frontend local)
    private static final List<String> LOCALHOST_ORIGIN = Arrays.asList(
            "http://localhost:4200", // Angular dev server
            "http://localhost:8080"  // Otro origen local posible
    );

    // Métodos HTTP permitidos para CORS
    private static final List<String> ALLOWED_METHODS = Arrays.asList(
            GET.name(), POST.name(), PUT.name(), DELETE.name(), OPTIONS.name()
    );

    @Bean
    CorsWebFilter createCorsFilter() {
        // Configuración principal de CORS
        var config = new CorsConfiguration();
        config.setAllowCredentials(Boolean.TRUE); // Permite envío de cookies / credenciales
        config.setAllowedOrigins(LOCALHOST_ORIGIN); // Define los orígenes permitidos
        config.setAllowedMethods(ALLOWED_METHODS); // Define los métodos HTTP permitidos
        config.setAllowedHeaders(Collections.singletonList(CorsConfiguration.ALL)); // Permite todos los headers

        // Fuente basada en URLs para aplicar la configuración CORS
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Aplica CORS a todas las rutas

        return new CorsWebFilter(source); // Devuelve el filtro que Spring aplicará a cada request
    }
}
