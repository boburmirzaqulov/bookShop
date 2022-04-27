package uz.yt.springdata.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.yt.springdata.dao.Author;
import uz.yt.springdata.dao.Book;
import uz.yt.springdata.dto.AuthorDTO;
import uz.yt.springdata.dto.BookDTO;
import uz.yt.springdata.dto.ResponseDTO;
import uz.yt.springdata.mapping.AuthorMapping;
import uz.yt.springdata.repository.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    public boolean existsById(Integer id){
        return authorRepository.existsById(id);
    }

    public ResponseDTO<Page<AuthorDTO>> getAllAuthors(Integer size, Integer page) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Author> authorPage = authorRepository.findAll(pageRequest);
            List<AuthorDTO> authors = authorPage
                    .stream()
                    .map(e -> AuthorMapping.toDto(e, e.getBooks()))
                    .collect(Collectors.toList());
            Page<AuthorDTO> result = new PageImpl<>(authors, authorPage.getPageable(), authorPage.getTotalPages());
            return new ResponseDTO<>(true, 0, "OK", result, null);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseDTO<>(false, -2, "Ma'lumot qidirishda xatolik mavjud", null, null);
        }
    }
}
