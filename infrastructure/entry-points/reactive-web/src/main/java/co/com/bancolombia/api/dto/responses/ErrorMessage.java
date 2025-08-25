package co.com.bancolombia.api.dto.responses;

import java.time.LocalDateTime;

public record ErrorMessage(
        int statusCode,
        LocalDateTime timestamp,
        String message,
        String description
) {}

