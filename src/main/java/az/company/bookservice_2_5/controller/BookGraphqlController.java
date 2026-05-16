package az.company.bookservice_2_5.controller;

import az.company.bookservice_2_5.model.response.BookResponse;
import az.company.bookservice_2_5.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BookGraphqlController {

    private final BookService bookService;

    @QueryMapping
    public Map<String, Object> allBooks(@Argument int page, @Argument int size) {
        Page<BookResponse> bookPage = bookService.getAllBooks(PageRequest.of(page, size));
        return Map.of(
                "content", bookPage.getContent(),
                "totalElements", bookPage.getTotalElements(),
                "totalPages", bookPage.getTotalPages()
        );
    }

    @QueryMapping
    public BookResponse bookByIsbn(@Argument String isbn) {
        return bookService.getByIsbn(isbn);
    }
}
