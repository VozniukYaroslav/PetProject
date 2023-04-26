package mapper;

import dto.CreateStockDto;
import entity.Order;
import entity.Stock;

public class CreateStockMapper implements Mapper<CreateStockDto, Stock> {

    private static final CreateStockMapper INSTANCE = new CreateStockMapper();
    @Override
    public Stock mapFrom(CreateStockDto object) {
        return Stock.builder()
                .bookId(Long.valueOf(object.getBookId()))
                .count(Integer.valueOf(object.getCount()))
                .build();
    }

    public Stock mapFromWithOrder(CreateStockDto object, Order newOrder) {
        return Stock.builder()
                .bookId(Long.valueOf(object.getBookId()))
                .count(Integer.valueOf(object.getCount()))
                .orderId(newOrder.getId())
                .build();
    }

    public static CreateStockMapper getInstance() {
        return INSTANCE;
    }
}
