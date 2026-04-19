package az.company.bookservice_2_5.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponse {
    private Long id;
    private String title;
    private String isbn;
    private AuthorResponse author;
}
