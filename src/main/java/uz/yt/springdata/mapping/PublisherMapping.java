package uz.yt.springdata.mapping;

import uz.yt.springdata.dao.Publisher;
import uz.yt.springdata.dto.PublisherDTO;

public class PublisherMapping {
    public static PublisherDTO toDto(Publisher publisher){
        if (publisher==null) return null;
        return new PublisherDTO(
                publisher.getId(),
                publisher.getName(),
                publisher.getAddressId()
        );
    }

    public static Publisher toEntity(PublisherDTO publisherDTO){
        if (publisherDTO==null) return null;
        return new Publisher(
                publisherDTO.getId(),
                publisherDTO.getName(),
                publisherDTO.getAddressId()
        );
    }
}
