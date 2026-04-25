package az.company.bookservice_2_5.config;

import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheManagerConfig {
    @Bean
    public org.springframework.cache.CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("userCount");
    }
}
