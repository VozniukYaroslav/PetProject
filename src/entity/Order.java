package entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class Order {
    Long id;
    String customerName;
    LocalDate dateOfOrder;
    BigDecimal sumOfOrder;
}
