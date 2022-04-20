package uz.yt.springdata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String birthDate;

    public AuthorDTO(Integer id) {
        this.id = id;
    }
}
