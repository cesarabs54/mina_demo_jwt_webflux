package co.com.bancolombia.api.dto.responses;

public record TokenRefreshResponse(
        String accessToken,
        String refreshToken
) {
    public static final String TOKEN_TYPE = "Bearer";
}
