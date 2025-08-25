package co.com.bancolombia.api.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record LogOutRequest(
        @NotBlank(message = "El ID de usuario es obligatorio")
        String userId
) {

}

