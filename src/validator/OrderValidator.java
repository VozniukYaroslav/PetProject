package validator;

import dto.CreateOrderDto;
import dto.OrderDto;

public class OrderValidator implements Validator<CreateOrderDto>{

    private static final OrderValidator INSTANCE = new OrderValidator();


    @Override
    public ValidationResult isValid(CreateOrderDto object) {
        var validationResult = new ValidationResult();
        if (object.getCustomerName().isEmpty()){
            validationResult.addError(Error.of("empty.customerName","Customer name is empty"));
        }if (object.getStockDtoList().isEmpty()){
            validationResult.addError(Error.of("empty.bookOrder","You did not add any books"));
        }
        return validationResult;
    }

    public static OrderValidator getInstance() {
        return INSTANCE;
    }
}
