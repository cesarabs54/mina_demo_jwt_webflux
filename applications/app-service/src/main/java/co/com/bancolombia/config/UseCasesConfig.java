package co.com.bancolombia.config;

import co.com.bancolombia.model.gateways.JwtGateway;
import co.com.bancolombia.model.gateways.PasswordEncoderService;
import co.com.bancolombia.model.gateways.RefreshTokenRepository;
import co.com.bancolombia.model.gateways.RoleRepository;
import co.com.bancolombia.model.gateways.UserRepository;
import co.com.bancolombia.usecase.AuthenticateUserUseCase;
import co.com.bancolombia.usecase.GetUserByUsernameUseCase;
import co.com.bancolombia.usecase.LogoutUserUseCase;
import co.com.bancolombia.usecase.RegisterUserUseCase;
import co.com.bancolombia.usecase.api.security.AuthenticateUserUseCaseImpl;
import co.com.bancolombia.usecase.api.security.CreateRefreshTokenUseCase;
import co.com.bancolombia.usecase.api.security.GetUserByUsernameUseCaseImpl;
import co.com.bancolombia.usecase.api.security.JwtUseCase;
import co.com.bancolombia.usecase.api.security.LogoutUserUseCaseImpl;
import co.com.bancolombia.usecase.api.security.RefreshAccessTokenUseCase;
import co.com.bancolombia.usecase.api.security.RefreshTokenUseCase;
import co.com.bancolombia.usecase.api.security.RegisterUserUseCaseImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "co.com.bancolombia.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
public class UseCasesConfig {

    @Bean
    public GetUserByUsernameUseCase getUserByUsernameUseCase(UserRepository userRepository) {
        return new GetUserByUsernameUseCaseImpl(userRepository);
    }

    @Bean
    public CreateRefreshTokenUseCase createRefreshTokenUseCase(
            RefreshTokenRepository refreshTokenRepository,
            UserRepository userRepository) {
        return new CreateRefreshTokenUseCase(refreshTokenRepository, userRepository);
    }

    @Bean
    public RefreshTokenUseCase refreshTokenUseCase(
            RefreshTokenRepository refreshTokenRepository,
            UserRepository userRepository,
            @Value("${bezkoder.app.jwtRefreshExpirationMs}") long durationMs
    ) {
        return new RefreshTokenUseCase(refreshTokenRepository, userRepository, durationMs);
    }

    @Bean
    public JwtUseCase jwtUseCase(JwtGateway jwtGateway) {
        return new JwtUseCase(jwtGateway);
    }


    @Bean
    public AuthenticateUserUseCase authenticateUserUseCase(UserRepository userRepository,
            JwtGateway jwtGateway, PasswordEncoderService passwordEncoderService) {
        return new AuthenticateUserUseCaseImpl(userRepository, jwtGateway, passwordEncoderService);
    }

    @Bean
    public RegisterUserUseCase registerUserUseCase(UserRepository userRepository,
            RoleRepository roleRepository, PasswordEncoderService passwordEncoderService) {
        return new RegisterUserUseCaseImpl(userRepository, roleRepository, passwordEncoderService);
    }

    @Bean
    public LogoutUserUseCase logoutUserUseCase(JwtGateway jwtGateway) {
        return new LogoutUserUseCaseImpl(jwtGateway);
    }

    @Bean
    public RefreshAccessTokenUseCase refreshAccessTokenUseCase(
            RefreshTokenUseCase refreshTokenUseCase,
            JwtGateway jwtGateway
    ) {
        return new RefreshAccessTokenUseCase(refreshTokenUseCase, jwtGateway);
    }
}
