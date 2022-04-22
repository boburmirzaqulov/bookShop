package uz.yt.springdata.mapping;

import uz.yt.springdata.dao.Publisher;
import uz.yt.springdata.dto.PublisherDTO;

public class PublisherMapping {
    public static PublisherDTO toDto(Publisher publisher){
        return publisher == null ? null : new PublisherDTO(
                publisher.getId(),
                publisher.getName(),
                AddressMapping.toDto(publisher.getAddress())
        );
    }

    public static Publisher toEntity(PublisherDTO publisherDTO){
        return publisherDTO == null ? null : new Publisher(
                publisherDTO.getId(),
                publisherDTO.getName(),
                AddressMapping.toEntity(publisherDTO.getAddress())
        );
    }
}
