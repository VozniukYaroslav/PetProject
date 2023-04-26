package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.BookService;
import service.StockService;
import util.JspUtil;

import java.io.IOException;

@WebServlet("/detail-order")
public class DetailOrderServlet extends HttpServlet {
    private final StockService stockService = StockService.getInstance();
    private final BookService bookService = BookService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var orderId = req.getParameter("orderId");
        req.setAttribute("stock",stockService.findByOrderId(orderId));
        req.setAttribute("books",bookService.findAll());
        req.getRequestDispatcher(JspUtil.getJspPath("detailOrder"))
                .forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
