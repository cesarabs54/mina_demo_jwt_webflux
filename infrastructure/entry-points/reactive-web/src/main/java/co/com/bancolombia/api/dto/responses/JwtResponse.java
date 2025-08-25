package co.com.bancolombia.api.dto.responses;

import com.fasterxml.jackson.annotation.JsonGetter;
import java.util.List;

public record JwtResponse(
        String token,
        String refreshToken,
        String userId,
        String name,
        String username,
        String email,
        List<String> roles
) {
    private static final String TOKEN_TYPE = "Bearer";

    @JsonGetter("tokenType")
    public String tokenType() {
        return TOKEN_TYPE;
    }
}

