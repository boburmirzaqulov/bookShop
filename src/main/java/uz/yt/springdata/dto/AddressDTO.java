package uz.yt.springdata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private Integer id;
    private Integer regionId;
    private Integer districtId;
    private String street;
    private String homeNumber;
    private String orient;
}
