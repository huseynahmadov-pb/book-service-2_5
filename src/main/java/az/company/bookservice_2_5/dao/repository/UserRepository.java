package az.company.bookservice_2_5.dao.repository;

import az.company.bookservice_2_5.dao.entity.UserEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    @Cacheable("userCount")
    @Query("SELECT COUNT(u) FROM UserEntity u")
    Long findUserCount();
}
