package uz.yt.springdata.mapping;

import uz.yt.springdata.dao.Address;
import uz.yt.springdata.dto.AddressDTO;

public class AddressMapping {
    public static AddressDTO toDto(Address address){
        return address == null ? null : new AddressDTO(
                address.getId(),
                address.getRegionId(),
                address.getRegionId(),
                address.getStreet(),
                address.getHomeNumber(),
                address.getOrient()
        );
    }
    public static Address toEntity(AddressDTO addressDTO){
        return addressDTO == null ? null : new Address(
                addressDTO.getId(),
                addressDTO.getStreet(),
                addressDTO.getHomeNumber(),
                addressDTO.getOrient(),
                addressDTO.getRegionId(),
                addressDTO.getDistrictId()
        );
    }
}
