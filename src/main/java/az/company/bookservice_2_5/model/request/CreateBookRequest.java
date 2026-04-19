package az.company.bookservice_2_5.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreateBookRequest {

    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "ISBN must not be blank")
    private String isbn;

    @NotNull(message = "Price must not be null")
    private BigDecimal price;
    private Long authorId;
}
