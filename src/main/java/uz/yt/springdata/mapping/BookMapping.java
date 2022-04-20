package uz.yt.springdata.mapping;

import uz.yt.springdata.dao.Book;
import uz.yt.springdata.dto.AuthorDTO;
import uz.yt.springdata.dto.BookDTO;
import uz.yt.springdata.dto.PublisherDTO;

import java.sql.Date;

public class BookMapping {
    public static BookDTO toDto(Book book){

        return new BookDTO(
                book.getId(),
                book.getNameUz(),
                book.getCost(),
                book.getPublishedDate().toString(),
                book.getPageCount(),
                new AuthorDTO(book.getAuthorId()),
                book.getGenre(),
                new PublisherDTO(book.getPublisherId())
        );
    }

    public static Book toEntity(BookDTO bookDTO){
        return new Book(
                bookDTO.getId(),
                bookDTO.getName(),
                null,
                bookDTO.getCost(),
                Date.valueOf(bookDTO.getPublishedDate()),
                bookDTO.getPageCount(),
                bookDTO.getAuthor().getId(),
                bookDTO.getGenre(),
                bookDTO.getPublisher().getId()
        );
    }

    public static void setDto(BookDTO bookDTO, Book book) {
        bookDTO.setId(book.getId());
        bookDTO.setName(book.getNameUz());
        bookDTO.setCost(book.getCost());
        bookDTO.setPublishedDate(book.getPublishedDate().toString());
        bookDTO.setPageCount(book.getPageCount());
        bookDTO.setGenre(book.getGenre());
    }
}
