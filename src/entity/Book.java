package entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Book {
    Long id;
    String name;
    String author;
    LocalDate year;
    Genre genre;
    Integer cost;


}
