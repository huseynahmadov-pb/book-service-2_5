package az.company.bookservice_2_5.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final String code;

    public NotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }
}
