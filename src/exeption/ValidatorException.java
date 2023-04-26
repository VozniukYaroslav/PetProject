package exeption;

import lombok.Getter;
import validator.Error;

import java.util.List;

public class ValidatorException extends RuntimeException {
    @Getter
    private final List<Error> errors;


    public ValidatorException(List<Error> errors) {
        this.errors = errors;
    }
}
