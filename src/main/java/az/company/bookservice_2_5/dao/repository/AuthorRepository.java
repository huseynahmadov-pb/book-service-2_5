package az.company.bookservice_2_5.dao.repository;

import az.company.bookservice_2_5.dao.entity.AuthorEntity;
import az.company.bookservice_2_5.dao.entity.BookEntity;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {
}
