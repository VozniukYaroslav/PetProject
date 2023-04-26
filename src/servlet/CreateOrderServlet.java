package servlet;

import dao.BookDao;
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

@WebServlet("/create-order")
public class CreateOrderServlet extends HttpServlet {
    private final OrderService orderService = OrderService.getInstance();
    private final BookDao bookDao = BookDao.getInstance();
    private final BookService bookService = BookService.getInstance();

    private final StockService stockService = StockService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("books", bookDao.findAll());
        req.getRequestDispatcher(JspUtil.getJspPath("createOrder"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var bookIds = req.getParameterValues("bookId");
        var bookDtoList = stockService.createBookDtoList(bookIds, req);
        var customerName = CreateOrderDto.builder()
                .customerName(req.getParameter("customerName"))
                .stockDtoList(bookDtoList)
                .build();

        try {
            orderService.create(customerName);
            resp.sendRedirect("/order-list");
        } catch (ValidatorException exception) {
            req.setAttribute("error", exception.getErrors());
            doGet(req, resp);
        }
     }
}
