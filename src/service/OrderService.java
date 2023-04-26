package service;

import dao.OrderDao;
import dto.CreateOrderDto;
import entity.Order;
import exeption.ValidatorException;
import mapper.CreateOrderMapper;
import util.LocalDateUtil;
import validator.ByDateIntervalValidator;
import validator.OrderValidator;

import java.util.List;
import java.util.Optional;

public class OrderService {

    private static final OrderService INSTANCE = new OrderService();
    private final OrderValidator orderValidator = OrderValidator.getInstance();
    private final CreateOrderMapper mapper = CreateOrderMapper.getInstance();
    private final StockService stockService = StockService.getInstance();
    private final OrderDao orderDao = OrderDao.getInstance();
    private final ByDateIntervalValidator byDateIntervalValidator = ByDateIntervalValidator.getInstance();


    public void create(CreateOrderDto dto) {
        var orderValidatorValid = orderValidator.isValid(dto);
        if (!orderValidatorValid.isValid()) {
            throw new ValidatorException(orderValidatorValid.getErrors());
        }
        var mapOrderDto = mapper.mapOrderDto(dto);
        var newOrder = orderDao.createNewOrder(mapOrderDto);
        stockService.addNewStock(dto,newOrder);
    }

    public void update(CreateOrderDto dto,String id){
        var orderValidatorValid = orderValidator.isValid(dto);
        if (!orderValidatorValid.isValid()){
            throw new ValidatorException(orderValidatorValid.getErrors());
        }
        var value = Long.valueOf(id);
        var orderEntity = mapper.mapOrderDto(dto);
        var newOrder = orderDao.updateOrder(orderEntity,value);
        stockService.update(dto,newOrder);
    }

    public List<Order> findAll() {
        return orderDao.findAll();
    }

    public Optional<Order> findById(String id){
        return orderDao.findById(Long.valueOf(id));
    }

    public void deleteOrder(Long id) {
        stockService.deleteById(id);
        orderDao.findById(id)
                .ifPresent(order -> orderDao.deleteOrder(id));
    }

    public List<Order> findByDateInterval(String from, String to) {
        var validationResult = byDateIntervalValidator.isValid(List.of(from, to));
        if (!validationResult.isValid()) {
            throw new ValidatorException(validationResult.getErrors());
        }
        return orderDao.findByDateInterval(LocalDateUtil.format(from), LocalDateUtil.format(to));
    }

    public static OrderService getInstance() {
        return INSTANCE;
    }
}
