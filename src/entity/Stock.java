package entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Stock {

    Long id;
    Long orderId;
    Long bookId ;
    Integer count;
}
