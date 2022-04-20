package uz.yt.springdata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Integer id;
    private String name;
    private BigDecimal cost;
    private String publishedDate;
    private Integer pageCount;
    private AuthorDTO authorDTO;
    private String genre;
    private PublisherDTO publisherDTO;

    public BookDTO(Integer id) {
        this.id = id;
    }
}
