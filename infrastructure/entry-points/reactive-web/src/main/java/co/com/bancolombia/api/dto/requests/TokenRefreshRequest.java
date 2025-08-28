package co.com.bancolombia.api.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Petición para refrescar el token de acceso usando un refresh token")
public record TokenRefreshRequest(

        @NotBlank(message = "El token de refresco no puede estar vacío")
        @Schema(description = "Refresh token válido para generar un nuevo access token", example = "eyJhbGciOiJIUzUxMiJ9...")
        String refreshToken
) {}
