package uz.yt.springdata.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.yt.springdata.dto.BookDTO;
import uz.yt.springdata.dto.ResponseDTO;
import uz.yt.springdata.repository.BookRepository;
import uz.yt.springdata.service.AuthorService;
import uz.yt.springdata.service.PublisherService;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class BookValid {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final PublisherService publisherService;

    public ResponseDTO<BookDTO> validCost(BookDTO bookDTO){
        if (bookDTO.getCost().intValue() < 0)
            return new ResponseDTO<>(false, -7, "Kitob narxi manfiy son bo'lishi mumkinmas", bookDTO);
        return null;
    }

    public ResponseDTO<BookDTO> validPublishedDate(BookDTO bookDTO){
        if (!Pattern.matches("\\d{4}-\\d{2}-\\d{2}",bookDTO.getPublishedDate().toString())) {
            return new ResponseDTO<>(false, -7, "Sana formati mos kelmadi. To'g'ri format YYYY-MM-DD", bookDTO);
        }
        return null;
    }
    public ResponseDTO<BookDTO> validPageCount(BookDTO bookDTO){
        if (bookDTO.getPageCount()<0){
            return new ResponseDTO<>(false, -7, "Kitob betlari soni manfiy bo'lishi mumkinmas", bookDTO);
        }
        return null;
    }
    public ResponseDTO<BookDTO> validAuthorId(BookDTO bookDTO){
        Integer id = bookDTO.getAuthorDTO().getId();
        if (id == null || !authorService.existsById(id)) {
            return new ResponseDTO<>(false, -7, String.format("Berilgan authorID = %d mavjud emas", id), bookDTO);
        }
        return null;
    }
    public ResponseDTO<BookDTO> validPublisherId(BookDTO bookDTO){
        Integer id = bookDTO.getPublisherDTO().getId();
        if (id == null || !publisherService.existsById(id)) {
            return new ResponseDTO<>(false, -7, String.format("Berilgan publisherID = %d mavjud emas", id), bookDTO);
        }
        return null;
    }
    public ResponseDTO<BookDTO> validBookId(BookDTO bookDTO){
        Integer id =bookDTO.getId();
        if (id == null || !bookRepository.existsById(id)){
            return new ResponseDTO<>(false, -7, String.format("Berilgan bookID = %d mavjud emas", id), bookDTO);
        }
        return null;
    }
    public ResponseDTO<BookDTO> validAuthorIdAndPublisherId(BookDTO bookDTO){
        ResponseDTO<BookDTO> responseDTO = validAuthorId(bookDTO);
        if (responseDTO != null) return responseDTO;
        responseDTO = validPublisherId(bookDTO);
        if (responseDTO != null) return responseDTO;
        return new ResponseDTO<>(true, 0, "OK", bookDTO);
    }

    public ResponseDTO<BookDTO> validAll(BookDTO bookDTO, Integer id){
        ResponseDTO<BookDTO> responseDTO = validCost(bookDTO);
        if (responseDTO != null) return responseDTO;
        responseDTO = validPublishedDate(bookDTO);
        if (responseDTO != null) return responseDTO;
        responseDTO = validPageCount(bookDTO);
        if (responseDTO != null) return responseDTO;
        responseDTO = validAuthorId(bookDTO);
        if (responseDTO != null) return responseDTO;
        responseDTO = validPublisherId(bookDTO);
        if (responseDTO != null) return responseDTO;
        if (id != null) {
            responseDTO = validBookId(bookDTO);
            if (responseDTO != null) return responseDTO;
        }
        return new ResponseDTO<>(true, 0, "OK", bookDTO);
    }
}
