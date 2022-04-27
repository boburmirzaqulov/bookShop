package uz.yt.springdata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublisherDTO {
    private Integer id;
    private String name;
    private AddressDTO address;
    private List<BookDTO> books;

    public PublisherDTO(Integer id) {
        this.id = id;
    }
}
