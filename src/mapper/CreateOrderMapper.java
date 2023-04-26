package mapper;

import dao.BookDao;
import dto.CreateOrderDto;
import entity.Order;

import java.math.BigDecimal;
import java.util.stream.Collectors;

public class CreateOrderMapper {

    private static final CreateOrderMapper INSTANCE = new CreateOrderMapper();
    private final BookDao bookDao = BookDao.getInstance();
    private final CreateStockMapper stockMapper = CreateStockMapper.getInstance();

    public Order mapOrderDto(CreateOrderDto orderDto){
        var bookList = orderDto.getStockDtoList().stream()
                .map(stockMapper::mapFrom)
                .collect(Collectors.toList());

        var order = Order.builder()
                .customerName(orderDto.getCustomerName())
                .sumOfOrder(BigDecimal.valueOf(bookList.stream()
                        .mapToInt(book -> book.getCount() * bookDao.findById(book.getBookId()).get().getCost())
                        .sum()))
                .build();

        return order;
    }

    public static CreateOrderMapper getInstance() {
        return INSTANCE;
    }
}
