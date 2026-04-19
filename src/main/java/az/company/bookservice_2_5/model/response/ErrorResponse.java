package az.company.bookservice_2_5.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ErrorResponse {
    private String code;
    private String message;
    private LocalDateTime timestamp;
    private List<String> validationErrors;
}
