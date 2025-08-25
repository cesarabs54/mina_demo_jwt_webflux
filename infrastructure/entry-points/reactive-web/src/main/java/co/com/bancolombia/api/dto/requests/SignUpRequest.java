package co.com.bancolombia.api.dto.requests;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record SignUpRequest(
        @NotBlank
        @Size(min = 3, max = 20)
        String username,

        @NotBlank
        @Size(min = 3, max = 50)
        String name,

        @NotBlank
        @Size(max = 50)
        @Email
        String email,

        Set<String> roles,

        @NotBlank
        @Size(min = 6, max = 40)
        String password
) {}
