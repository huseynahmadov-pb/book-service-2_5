package az.company.bookservice_2_5.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAuthorRequest {
    @NotBlank(message = "Author name must not be blank")
    private String name;
}
