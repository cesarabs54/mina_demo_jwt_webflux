package co.com.bancolombia.api.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta para la generación de un nuevo token de acceso a partir de un refresh token")
public record TokenRefreshResponse(

        @Schema(description = "Nuevo token de acceso JWT", example = "eyJhbGciOiJIUzUxMiJ9.newAccessToken...")
        String accessToken,

        @Schema(description = "Refresh token utilizado o renovado", example = "eyJhbGciOiJIUzUxMiJ9.refreshToken...")
        String refreshToken
) {

    @Schema(description = "Tipo de token", example = "Bearer")
    public static final String TOKEN_TYPE = "Bearer";
}
