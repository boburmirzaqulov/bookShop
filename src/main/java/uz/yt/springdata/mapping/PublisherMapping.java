package uz.yt.springdata.mapping;

import uz.yt.springdata.dao.Book;
import uz.yt.springdata.dao.Publisher;
import uz.yt.springdata.dto.BookDTO;
import uz.yt.springdata.dto.PublisherDTO;

import java.util.List;
import java.util.stream.Collectors;

public class PublisherMapping {
    public static PublisherDTO toDto(Publisher publisher, List<Book> books){
        if (publisher == null) return null;
        List<BookDTO> books2 = null;
        if (books != null) {
            books2 = books
                    .stream()
                    .map(e -> BookMapping.toDto(e, e.getAuthor(), null))
                    .collect(Collectors.toList());
        }
        return  new PublisherDTO(
                publisher.getId(),
                publisher.getName(),
                AddressMapping.toDto(publisher.getAddress()),
                books2
        );
    }

    public static Publisher toEntity(PublisherDTO publisherDTO){
        return publisherDTO == null ? null : new Publisher(
                publisherDTO.getId(),
                publisherDTO.getName(),
                AddressMapping.toEntity(publisherDTO.getAddress()),
                null
        );
    }
}
