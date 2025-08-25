package co.com.bancolombia.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class SecurityHeadersConfig implements WebFilter {

    // Constantes de los nombres de los encabezados de seguridad HTTP
    private static final String CONTENT_SECURITY_POLICY = "Content-Security-Policy";
    private static final String STRICT_TRANSPORT_SECURITY = "Strict-Transport-Security";
    private static final String X_CONTENT_TYPE_OPTIONS = "X-Content-Type-Options";
    private static final String CACHE_CONTROL = "Cache-Control";
    private static final String PRAGMA = "Pragma";
    private static final String REFERRER_POLICY = "Referrer-Policy";

    // Valores configurables a través de application.properties o application.yml
    @Value("${security.headers.content-security-policy:default-src 'self'; frame-ancestors 'self'; form-action 'self'}")
    private String contentSecurityPolicy; // Política de seguridad de contenido

    @Value("${security.headers.strict-transport-security:max-age=31536000;}")
    private String strictTransportSecurity; // HSTS para forzar HTTPS

    @Value("${security.headers.x-content-type-options:nosniff}")
    private String xContentTypeOptions; // Previene el sniffing de tipos MIME

    @Value("${security.headers.cache-control:no-store}")
    private String cacheControl; // Control de cacheo de respuestas sensibles

    @Value("${security.headers.pragma:no-cache}")
    private String pragmaValue; // Encabezado legacy de control de cache

    @Value("${security.headers.referrer-policy:strict-origin-when-cross-origin}")
    private String referrerPolicy; // Política de referrer

    @NonNull
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        HttpHeaders headers = exchange.getResponse().getHeaders();

        // Agrega los encabezados de seguridad HTTP a la respuesta
        headers.set(CONTENT_SECURITY_POLICY, contentSecurityPolicy);
        headers.set(STRICT_TRANSPORT_SECURITY, strictTransportSecurity);
        headers.set(X_CONTENT_TYPE_OPTIONS, xContentTypeOptions);
        headers.set(CACHE_CONTROL, cacheControl);
        headers.set(PRAGMA, pragmaValue);
        headers.set(REFERRER_POLICY, referrerPolicy);

        // Continúa con el chain de filtros
        return chain.filter(exchange);
    }
}
