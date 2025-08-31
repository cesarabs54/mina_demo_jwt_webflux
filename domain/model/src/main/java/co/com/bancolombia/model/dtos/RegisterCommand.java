package co.com.bancolombia.model.dtos;

import java.util.Set;

public record RegisterCommand(
        String username,
        String name,
        String email,
        String password,
        Set<String> roles
) {}

