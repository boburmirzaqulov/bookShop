package uz.yt.springdata.mapping;

import uz.yt.springdata.dao.Author;
import uz.yt.springdata.dto.AuthorDTO;

import java.sql.Date;

public class AuthorMapping {
    public static AuthorDTO toDto(Author author){
        return author == null ? null : new AuthorDTO(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getBirthDate().toString()
        );
    }

    public static Author toEntity(AuthorDTO authorDTO){
        return authorDTO == null ? null : new Author(
                authorDTO.getId(),
                authorDTO.getFirstName(),
                authorDTO.getLastName(),
                Date.valueOf(authorDTO.getBirthDate())
        );
    }
}
