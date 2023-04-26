package servlet;

import dto.CreateOrderDto;
import exeption.ValidatorException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.BookService;
import service.OrderService;
import service.StockService;
import util.JspUtil;

import java.io.IOException;

@WebServlet("/edit-order")
public class EditOrderServlet extends HttpServlet {

    private final BookService bookService = BookService.getInstance();
    private final StockService stockService = StockService.getInstance();
    private final OrderService orderService = OrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var orderDto = orderService.findById(req.getParameter("orderId"));
        if (orderDto.isPresent()){
            var order = orderDto.get();
            req.setAttribute("order",order);
            req.setAttribute("stock",stockService.findByOrderId(order));
            req.setAttribute("books",bookService.findAll());
            req.getRequestDispatcher(JspUtil.getJspPath("editOrder"))
                    .forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var bookId = req.getParameterValues("bookId");
        var orderId = req.getParameter("orderId");

        var dtoList = stockService.createBookDtoList(bookId, req);
        var customer = CreateOrderDto.builder()
                .customerName(req.getParameter("customerName"))
                .stockDtoList(dtoList)
                .build();

        try {
            orderService.update(customer,orderId);
            resp.sendRedirect("/order-list");
        } catch (ValidatorException exception) {
            req.setAttribute("error", exception.getErrors());
            doGet(req, resp);
        }
    }
}
