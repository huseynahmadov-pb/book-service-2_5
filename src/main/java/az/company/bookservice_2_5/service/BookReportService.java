package az.company.bookservice_2_5.service;

import az.company.bookservice_2_5.dao.repository.AuthorRepository;
import az.company.bookservice_2_5.dao.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookReportService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public void generateDailyReport() {
        log.info("Starting daily book report generation");

        long totalBooks = bookRepository.count();
        long totalAuthors = authorRepository.count();

        log.info("Daily Report - Total books: {}, Total authors: {}", totalBooks, totalAuthors);
        log.info("Daily book report generation completed");
    }
}
