package uz.yt.springdata.mapping;

import uz.yt.springdata.dao.Author;
import uz.yt.springdata.dao.Book;
import uz.yt.springdata.dto.AuthorDTO;
import uz.yt.springdata.dto.BookDTO;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorMapping {
    public static AuthorDTO toDto(Author author, List<Book> books){
        if (author == null) return null;
        List<BookDTO> books2 = null;
        if (books != null) {
            books2 = books
                    .stream()
                    .map(e -> BookMapping.toDto(e, null, null))
                    .collect(Collectors.toList());
        }
        return new AuthorDTO(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getBirthDate().toString(),
                books2
        );
    }

    public static Author toEntity(AuthorDTO authorDTO){
        return authorDTO == null ? null : new Author(
                authorDTO.getId(),
                authorDTO.getFirstName(),
                authorDTO.getLastName(),
                Date.valueOf(authorDTO.getBirthDate()),
                null
        );
    }
}
