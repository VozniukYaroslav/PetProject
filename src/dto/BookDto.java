package dto;

import entity.Genre;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class BookDto {
    Long id;
    String name;
    String author;
    String year;
    String genre;
    String cost;

}
