package co.com.bancolombia.api.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

@Schema(description = "Petición para registrar un nuevo usuario")
public record SignUpRequest(

        @NotBlank
        @Size(min = 3, max = 20)
        @Schema(description = "Nombre de usuario", example = "jdoe")
        String username,

        @NotBlank
        @Size(min = 3, max = 50)
        @Schema(description = "Nombre completo del usuario", example = "John Doe")
        String name,

        @NotBlank
        @Size(max = 50)
        @Email
        @Schema(description = "Correo electrónico válido", example = "jdoe@example.com")
        String email,

        @Schema(description = "Roles asignados al usuario", example = "[\"ROLE_USER\", \"ROLE_ADMIN\"]")
        Set<String> roles,

        @NotBlank
        @Size(min = 6, max = 40)
        @Schema(description = "Contraseña del usuario", example = "password123")
        String password
) {}
