package uz.yt.springdata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private List<BookDTO> books;

    public AuthorDTO(Integer id) {
        this.id = id;
    }
}
