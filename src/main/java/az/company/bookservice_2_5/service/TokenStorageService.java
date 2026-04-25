package az.company.bookservice_2_5.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenStorageService {

    private static final String ACCESS_TOKEN_PREFIX = "access_token:";
    private static final String REFRESH_TOKEN_PREFIX = "refresh_token:";

    private final StringRedisTemplate redisTemplate;
    private final JwtService jwtService;

    public void storeAccessToken(String username, String token) {
        String key = ACCESS_TOKEN_PREFIX + username;
        redisTemplate.opsForValue().set(key, token, jwtService.getAccessTokenExpiration(), TimeUnit.MINUTES);
    }

    public void storeRefreshToken(String username, String token) {
        String key = REFRESH_TOKEN_PREFIX + username;
        redisTemplate.opsForValue().set(key, token, jwtService.getRefreshTokenExpiration(), TimeUnit.MINUTES);
    }

    public boolean isAccessTokenValid(String username, String token) {
        String key = ACCESS_TOKEN_PREFIX + username;
        String storedToken = redisTemplate.opsForValue().get(key);
        return token.equals(storedToken);
    }

    public boolean isRefreshTokenValid(String username, String token) {
        String key = REFRESH_TOKEN_PREFIX + username;
        String storedToken = redisTemplate.opsForValue().get(key);
        return token.equals(storedToken);
    }

    public void removeAccessToken(String username) {
        redisTemplate.delete(ACCESS_TOKEN_PREFIX + username);
    }

    public void removeRefreshToken(String username) {
        redisTemplate.delete(REFRESH_TOKEN_PREFIX + username);
    }
}
