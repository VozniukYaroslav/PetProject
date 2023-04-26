package dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateOrderDto {

    String customerName;
    List<CreateStockDto> stockDtoList;


}
