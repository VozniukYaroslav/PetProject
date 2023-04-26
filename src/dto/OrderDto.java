package dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Data
@Builder
public class OrderDto {
    Long id;
    String customerName;
    LocalDate dateOfOrder;
    List<BookDto> booksDto;
    String totalSum;
}
