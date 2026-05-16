package az.company.bookservice_2_5.grpc.server;

import az.company.bookservice_2_5.grpc.AuthorProto;
import az.company.bookservice_2_5.grpc.BookGrpcServiceGrpc;
import az.company.bookservice_2_5.grpc.BookListProto;
import az.company.bookservice_2_5.grpc.BookProto;
import az.company.bookservice_2_5.grpc.GetAllBooksRequest;
import az.company.bookservice_2_5.grpc.GetBookByIsbnRequest;
import az.company.bookservice_2_5.model.response.BookResponse;
import az.company.bookservice_2_5.service.BookService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookGrpcServer extends BookGrpcServiceGrpc.BookGrpcServiceImplBase {

    private final BookService bookService;

    @Override
    public void getBookByIsbn(GetBookByIsbnRequest request, StreamObserver<BookProto> responseObserver) {
        BookResponse book = bookService.getByIsbn(request.getIsbn());
        responseObserver.onNext(toProto(book));
        responseObserver.onCompleted();
    }

    @Override
    public void getAllBooks(GetAllBooksRequest request, StreamObserver<BookListProto> responseObserver) {
        int page = Math.max(request.getPage(), 0);
        int size = request.getSize() > 0 ? request.getSize() : 10;
        var bookPage = bookService.getAllBooks(PageRequest.of(page, size));

        BookListProto.Builder listBuilder = BookListProto.newBuilder()
                .setTotalElements(bookPage.getTotalElements())
                .setTotalPages(bookPage.getTotalPages());

        bookPage.getContent().forEach(book -> listBuilder.addBooks(toProto(book)));

        responseObserver.onNext(listBuilder.build());
        responseObserver.onCompleted();
    }

    private BookProto toProto(BookResponse book) {
        BookProto.Builder builder = BookProto.newBuilder()
                .setId(book.getId())
                .setTitle(book.getTitle())
                .setIsbn(book.getIsbn());

        if (book.getAuthor() != null) {
            builder.setAuthor(AuthorProto.newBuilder()
                    .setId(book.getAuthor().getId())
                    .setName(book.getAuthor().getName())
                    .build());
        }

        return builder.build();
    }
}
