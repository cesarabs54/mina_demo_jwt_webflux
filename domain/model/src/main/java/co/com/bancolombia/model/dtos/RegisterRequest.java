package co.com.bancolombia.model.dtos;

import java.util.Set;

public record RegisterRequest(
        String username,
        String name,
        String email,
        String password,
        Set<String> roles
) {}

