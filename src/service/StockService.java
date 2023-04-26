package service;

import dao.StockDao;
import dto.CreateOrderDto;
import dto.CreateStockDto;
import entity.Order;
import entity.Stock;
import jakarta.servlet.http.HttpServletRequest;
import mapper.CreateStockMapper;

import java.util.ArrayList;
import java.util.List;

public class StockService {

    private static final StockService INSTANCE = new StockService();
    private final StockDao stockDao = StockDao.getInstance();
    private final CreateStockMapper createStockMapper = CreateStockMapper.getInstance();

    public void addNewStock(CreateOrderDto order, Order newOrder) {
        List<Stock> stocks = new ArrayList<>();
        for (CreateStockDto createStockDto : order.getStockDtoList()) {
            stocks.add(createStockMapper.mapFromWithOrder(createStockDto, newOrder));
        }
        for (Stock stock : stocks) {
            stockDao.create(stock);
        }
    }

    public List<Stock> findByOrderId(String orderId){
        return stockDao.findByOrderId(Long.valueOf(orderId));
    }

    public List<Stock> findByOrderId(Order order){
        Long orderId = order.getId();
        return stockDao.findByOrderId(orderId);
    }


    public List<CreateStockDto> createBookDtoList(String[] books, HttpServletRequest req) {
        List<CreateStockDto> createStockDtos = new ArrayList<>();

        if (books != null) {
            for (String book : books) {
                var parameter = req.getParameter(book);
                if (parameter != null) {
                    createStockDtos.add(CreateStockDto.builder()
                            .bookId(book)
                            .count(parameter)
                            .build());
                }
            }
        }
        return createStockDtos;
    }
    public void update(CreateOrderDto dto, Order newOrder) {
        List<Stock> stocks = new ArrayList<>();
        for (CreateStockDto createStockDto : dto.getStockDtoList()) {
            stocks.add(createStockMapper.mapFromWithOrder(createStockDto, newOrder));
        }
        delete(stocks);
        for (Stock stock : stocks) {
            stockDao.create(stock);
        }
    }

    public void deleteById(Long id){
        stockDao.delete(id);
    }

    public void delete(List<Stock> stocks){
        for (Stock stock : stocks) {
            stockDao.delete(stock.getOrderId());
        }
    }

    public static StockService getInstance() {
        return INSTANCE;
    }
}
