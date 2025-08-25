package co.com.bancolombia.api.dto.requests;


import jakarta.validation.constraints.NotBlank;

public record TokenRefreshRequest(
        @NotBlank(message = "El token de refresco no puede estar vacío")
        String refreshToken
) {}
