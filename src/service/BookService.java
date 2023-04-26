package service;

import dao.BookDao;
import dto.BookDto;
import dto.CreateBookDto;
import entity.Book;
import exeption.ValidatorException;
import jakarta.servlet.http.HttpServletRequest;
import mapper.BookMapper;
import validator.CreateBookValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookService {

    private static final BookService INSTANCE = new BookService();

    private final BookMapper bookMapper = BookMapper.getInstance();
    private final BookDao bookDao = BookDao.getInstance();
    private final CreateBookValidator bookValidator = CreateBookValidator.getInstance();

    public void findByName(String author){
        var byAuthor = bookDao.findByAuthor(author);

    }

    public Optional<Book> findByBookId(String bookId){
        return bookDao.findById(Long.valueOf(bookId));
    }

    public Long addNewBook(BookDto bookDto){
        var validatorValid = bookValidator.isValid(bookDto);
        if (!validatorValid.isValid()){
            throw new ValidatorException(validatorValid.getErrors());
        }
        var bookEntity = bookMapper.mapFrom(bookDto);
        var book = bookDao.createBook(bookEntity);
        return book.getId();
    }

    public List<Book> findAll(){
        return bookDao.findAll();
    }

    public List<CreateBookDto> createBookDtoList(String[] books, HttpServletRequest req) {
        List<CreateBookDto> bookDtoList = new ArrayList<>();

        if (books != null) {
            for (String book : books) {
                var parameter = req.getParameter(book);
                if (parameter != null) {
                    bookDtoList.add(CreateBookDto.builder()
                            .productId(book)
                            .count(parameter)
                            .build());
                }
            }
        }


        return bookDtoList;
    }

    public static BookService getInstance() {
        return INSTANCE;
    }


}
