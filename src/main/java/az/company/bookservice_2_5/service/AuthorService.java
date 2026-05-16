package az.company.bookservice_2_5.service;

import az.company.bookservice_2_5.dao.entity.AuthorEntity;
import az.company.bookservice_2_5.dao.repository.AuthorRepository;
import az.company.bookservice_2_5.exception.NotFoundException;
import az.company.bookservice_2_5.mapper.AuthorMapper;
import az.company.bookservice_2_5.model.request.CreateAuthorRequest;
import az.company.bookservice_2_5.model.response.AuthorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

import static az.company.bookservice_2_5.model.enums.ErrorMessage.AUTHOR_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public void createAuthor(CreateAuthorRequest request) {
        var entity = authorMapper.toEntity(request);
        authorRepository.save(entity);
    }

    public AuthorResponse getById(Long id) {
        var entity = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        AUTHOR_NOT_FOUND.name(),
                        String.format(AUTHOR_NOT_FOUND.getMessage(), id)));
        return authorMapper.toResponse(entity);
    }

    public List<AuthorResponse> getAllAuthors() {
        var entities = StreamSupport.stream(authorRepository.findAll().spliterator(), false)
                .toList();
        return authorMapper.toResponse(entities);
    }

    public AuthorEntity getEntityById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        AUTHOR_NOT_FOUND.name(),
                        String.format(AUTHOR_NOT_FOUND.getMessage(), id)));
    }
}
