package servlet;

import dto.BookDto;
import entity.Genre;
import exeption.ValidatorException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.BookService;
import util.JspUtil;

import java.io.IOException;


@WebServlet("/add-new-book")
public class BookServlet extends HttpServlet {

    private final BookService bookService = BookService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("genre", Genre.values());
        req.getRequestDispatcher(JspUtil.getJspPath("addNewBook"))
                .forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var bookDtoBuilder = BookDto.builder()
                .name(req.getParameter("name"))
                .author(req.getParameter("author"))
                .year(req.getParameter("year"))
                .genre(req.getParameter("genre"))
                .cost(req.getParameter("cost"))
                .build();

        try {
            bookService.addNewBook(bookDtoBuilder);
        }catch (ValidatorException e){
            req.setAttribute("error",e.getErrors());
            doGet(req,resp);
        }
    }
}
