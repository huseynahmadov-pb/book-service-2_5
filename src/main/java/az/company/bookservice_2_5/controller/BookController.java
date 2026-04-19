package az.company.bookservice_2_5.controller;

import az.company.bookservice_2_5.model.request.CreateBookRequest;
import az.company.bookservice_2_5.model.response.BookResponse;
import az.company.bookservice_2_5.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Void> createBook(@Valid @RequestBody CreateBookRequest request) {
        bookService.createBook(request);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<Page<BookResponse>> getAllBooks(Pageable pageable) {
        return ResponseEntity.ok(bookService.getAllBooks(pageable));
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookResponse> getByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.getByIsbn(isbn));
    }
}
