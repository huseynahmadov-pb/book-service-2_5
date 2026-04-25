package az.company.bookservice_2_5.service;

import az.company.bookservice_2_5.dao.entity.UserEntity;
import az.company.bookservice_2_5.dao.repository.UserRepository;
import az.company.bookservice_2_5.model.enums.Role;
import az.company.bookservice_2_5.model.request.AddRoleRequest;
import az.company.bookservice_2_5.model.request.LoginRequest;
import az.company.bookservice_2_5.model.request.RefreshTokenRequest;
import az.company.bookservice_2_5.model.request.RegisterRequest;
import az.company.bookservice_2_5.model.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenStorageService tokenStorageService;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CacheManager cacheManager;

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(userEntity);
        String refreshToken = jwtService.generateRefreshToken(userEntity);
        tokenStorageService.storeAccessToken(userEntity.getUsername(), accessToken);
        tokenStorageService.storeRefreshToken(userEntity.getUsername(), refreshToken);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public LoginResponse refresh(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        if (jwtService.isTokenValid(refreshToken) || !jwtService.isRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        String username = jwtService.extractUsername(refreshToken);

        if (!tokenStorageService.isRefreshTokenValid(username, refreshToken)) {
            throw new IllegalArgumentException("Refresh token is not active");
        }

        UserEntity userEntity = userDetailsService.loadUserByUsername(username);
        String newAccessToken = jwtService.generateAccessToken(userEntity);
        String newRefreshToken = jwtService.generateRefreshToken(userEntity);

        tokenStorageService.storeAccessToken(username, newAccessToken);
        tokenStorageService.storeRefreshToken(username, newRefreshToken);

        return LoginResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    public void register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists: " + request.getUsername());
        }

        UserEntity user;
        Long userCount;
        var userCountCache = cacheManager.getCache("users").get("userCount", Long.class);
        if (userCountCache == null) {
            userCount = userRepository.findUserCount();
            cacheManager.getCache("users").put("userCount", userCount); // 0
        }

        if (userCountCache == null) {
            user = UserEntity.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roles(Set.of(Role.ROLE_ADMIN, Role.ROLE_USER))
                    .build();
        } else {
            user = UserEntity.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roles(Set.of(Role.ROLE_USER))
                    .build();

        }
        userRepository.save(user);
    }

    public void addRole(AddRoleRequest request) {
        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + request.getUsername()));

        user.getRoles().add(request.getRole());
        userRepository.save(user);
    }

    public void logout(String username) {
        tokenStorageService.removeAccessToken(username);
        tokenStorageService.removeRefreshToken(username);
    }
}
