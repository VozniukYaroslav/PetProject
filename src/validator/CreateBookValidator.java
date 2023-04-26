package validator;

import dto.BookDto;
import util.LocalDateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;

public class CreateBookValidator implements Validator<BookDto> {

    private static final CreateBookValidator INSTANCE = new CreateBookValidator();

    @Override
    public ValidationResult isValid(BookDto object) {
        var validationResult = new ValidationResult();
        if (object.getAuthor().isEmpty()) {
            validationResult.addError(Error.of("empty.author", "Author Name is empty!"));
        }
        if (object.getName().isEmpty()) {
            validationResult.addError(Error.of("empty.name", "Book Name is empty!"));
        }
        if (LocalDateUtil.format(object.getYear()).isAfter(LocalDate.now())) {
            validationResult.addError(Error.of("invalid.year", "Invalid Year of book"));
        }
        if (Integer.parseInt(object.getCost()) <= 0) {
            validationResult.addError(Error.of("invalid.cost", "Book will cost more then 1 cu"));
        }

        return validationResult;
    }

    public static CreateBookValidator getInstance() {
        return INSTANCE;
    }
}
