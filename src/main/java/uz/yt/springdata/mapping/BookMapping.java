package uz.yt.springdata.mapping;

import uz.yt.springdata.dao.Author;
import uz.yt.springdata.dao.Book;
import uz.yt.springdata.dao.Publisher;
import uz.yt.springdata.dto.BookDTO;

import java.sql.Date;

public class BookMapping {

    public static BookDTO toDto(Book book, Author author, Publisher publisher){
        return book == null ? null : new BookDTO(
                book.getId(),
                book.getNameUz(),
                book.getCost(),
                book.getPublishedDate().toString(),
                book.getPageCount(),
                AuthorMapping.toDto(author, null),
                book.getGenre(),
                PublisherMapping.toDto(publisher, null)
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
