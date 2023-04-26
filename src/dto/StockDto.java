package dto;

import entity.Genre;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockDto {

    String country;
    Genre genre;
}
