package uz.yt.springdata.mapping;

import uz.yt.springdata.dao.Book;
import uz.yt.springdata.dto.BookDTO;

import java.sql.Date;

public class BookMapping {

    public static BookDTO toDto(Book book){
        return book == null ? null : new BookDTO(
                book.getId(),
                book.getNameUz(),
                book.getCost(),
                book.getPublishedDate().toString(),
                book.getPageCount(),
                AuthorMapping.toDto(book.getAuthor()),
                book.getGenre(),
                PublisherMapping.toDto(book.getPublisher())
        );
    }

    public static Book toEntity(BookDTO bookDTO){
        return bookDTO == null ? null : new Book(
                bookDTO.getId(),
                bookDTO.getName(),
                null,
                bookDTO.getCost(),
                Date.valueOf(bookDTO.getPublishedDate()),
                bookDTO.getPageCount(),
                AuthorMapping.toEntity(bookDTO.getAuthor()),
                bookDTO.getGenre(),
                PublisherMapping.toEntity(bookDTO.getPublisher())
        );
    }

    public static void setDto(BookDTO bookDTO, Book book) {
        if (book == null) {
            return;
        }
        bookDTO.setId(book.getId());
        bookDTO.setName(book.getNameUz());
        bookDTO.setCost(book.getCost());
        bookDTO.setPublishedDate(book.getPublishedDate().toString());
        bookDTO.setPageCount(book.getPageCount());
        bookDTO.setGenre(book.getGenre());
    }
}
