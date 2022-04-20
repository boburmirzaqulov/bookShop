package uz.yt.springdata.mapping;

import uz.yt.springdata.dao.Publisher;
import uz.yt.springdata.dto.PublisherDTO;

public class PublisherMapping {
    public static PublisherDTO toDto(Publisher publisher){
        return new PublisherDTO(
                publisher.getId(),
                publisher.getName(),
                publisher.getAddressId()
        );
    }

    public static Publisher toEntity(PublisherDTO publisherDTO){
        return new Publisher(
                publisherDTO.getId(),
                publisherDTO.getName(),
                publisherDTO.getAddressId()
        );
    }
}
