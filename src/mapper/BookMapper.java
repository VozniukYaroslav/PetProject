package mapper;

import dto.BookDto;
import entity.Book;
import entity.Genre;
import util.LocalDateUtil;

public class BookMapper implements Mapper<BookDto, Book> {

    private static final BookMapper INSTANCE = new BookMapper();

    @Override
    public Book mapFrom(BookDto object) {
        return Book.builder()
                .name(object.getName())
                .author(object.getAuthor())
                .year(LocalDateUtil.format(object.getYear()))
                .genre(Genre.valueOf(object.getGenre()))
                .cost(Integer.valueOf(object.getCost()))
                .build();

    }

    public static BookMapper getInstance() {
        return INSTANCE;
    }
}
