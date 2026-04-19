package az.company.bookservice_2_5.dao.repository;

import az.company.bookservice_2_5.dao.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    Optional<BookEntity> findByIsbn(String isbn);
    Page<BookEntity> findAll(Pageable pageable);
}
