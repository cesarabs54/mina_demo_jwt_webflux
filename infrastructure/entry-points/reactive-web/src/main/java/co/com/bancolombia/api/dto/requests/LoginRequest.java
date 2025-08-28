package co.com.bancolombia.api.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Petición para autenticación de usuario")
public record LoginRequest(

        @NotBlank(message = "El nombre de usuario es obligatorio")
        @Schema(description = "Nombre de usuario", example = "jdoe")
        String username,

        @NotBlank(message = "La contraseña es obligatoria")
        @Schema(description = "Contraseña del usuario", example = "password123")
        String password
) {}
