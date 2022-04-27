package uz.yt.springdata.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.yt.springdata.dao.Author;
import uz.yt.springdata.dao.Publisher;
import uz.yt.springdata.dto.AuthorDTO;
import uz.yt.springdata.dto.PublisherDTO;
import uz.yt.springdata.dto.ResponseDTO;
import uz.yt.springdata.mapping.AuthorMapping;
import uz.yt.springdata.mapping.PublisherMapping;
import uz.yt.springdata.repository.PublisherRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublisherService {
    private final PublisherRepository publisherRepository;
    public boolean existsById(Integer id){
        return publisherRepository.existsById(id);
    }

    public ResponseDTO<Page<PublisherDTO>> getAllPublishers(Integer size, Integer page) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Publisher> publisherPage = publisherRepository.findAll(pageRequest);
            List<PublisherDTO> publishers = publisherPage.stream().map(e -> PublisherMapping.toDto(e, e.getBooks())).collect(Collectors.toList());
            Page<PublisherDTO> result = new PageImpl<>(publishers, publisherPage.getPageable(), publisherPage.getTotalPages());
            return new ResponseDTO<>(true, 0, "OK", result, null);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseDTO<>(false, -2, "Ma'lumot qidirishda xatolik mavjud", null, null);
        }
    }
}
