package az.company.bookservice_2_5.service;

import az.company.bookservice_2_5.dao.repository.BookRepository;
import az.company.bookservice_2_5.exception.NotFoundException;
import az.company.bookservice_2_5.mapper.BookMapper;
import az.company.bookservice_2_5.model.request.CreateBookRequest;
import az.company.bookservice_2_5.model.response.BookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static az.company.bookservice_2_5.model.enums.ErrorMessage.BOOK_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorService authorService;

    public void createBook(CreateBookRequest request) {
        var entity = bookMapper.toEntity(request);
        if (request.getAuthorId() != null) {
            entity.setAuthors(authorService.getEntityById(request.getAuthorId()));
        }
        bookRepository.save(entity);
    }

    public Page<BookResponse> getAllBooks(Pageable pageable) {
        var entities = bookRepository.findAll(pageable);
        return new PageImpl<>(
                bookMapper.toResponse(entities.getContent()),
                pageable,
                entities.getTotalElements()
        );
    }

    public BookResponse getByIsbn(String isbn) {
        var entity = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new NotFoundException(
                        BOOK_NOT_FOUND.name(),
                        String.format(BOOK_NOT_FOUND.getMessage(), isbn)));
        return bookMapper.toResponse(entity);
    }
}
