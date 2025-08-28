package co.com.bancolombia.api.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Petición para cerrar la sesión del usuario")
public record LogOutRequest(

        @NotBlank(message = "El ID de usuario es obligatorio")
        @Schema(description = "Identificador único del usuario", example = "12345")
        String userId
) {

}
