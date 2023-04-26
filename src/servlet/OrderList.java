package servlet;

import entity.Book;
import entity.Order;
import entity.Stock;
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
import java.util.List;
import java.util.Optional;

@WebServlet("/order-list")
public class OrderList extends HttpServlet {
    private final OrderService orderService = OrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var orderId = req.getParameter("orderId");
        req.setAttribute("orders", orderService.findAll());
        req.getRequestDispatcher(JspUtil.getJspPath("orderList"))
                .forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var order = req.getParameter("deleteOrder");
        orderService.deleteOrder(Long.valueOf(order));
        doGet(req,resp);
    }
}
