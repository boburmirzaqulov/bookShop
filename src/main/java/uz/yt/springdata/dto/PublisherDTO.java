package uz.yt.springdata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublisherDTO {
    private Integer id;
    private String name;
    private AddressDTO address;

    public PublisherDTO(Integer id) {
        this.id = id;
    }
}
