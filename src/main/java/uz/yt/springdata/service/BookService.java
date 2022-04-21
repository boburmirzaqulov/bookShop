package uz.yt.springdata.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.yt.springdata.dao.Book;
import uz.yt.springdata.dto.BookDTO;
import uz.yt.springdata.dto.ResponseDTO;
import uz.yt.springdata.mapping.AuthorMapping;
import uz.yt.springdata.mapping.BookMapping;
import uz.yt.springdata.mapping.PublisherMapping;
import uz.yt.springdata.repository.AuthorRepository;
import uz.yt.springdata.repository.BookRepository;
import uz.yt.springdata.repository.PublisherRepository;
import uz.yt.springdata.validation.BookValid;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final BookValid bookValid;

    public ResponseDTO<Page<BookDTO>> getAllBooks(Integer size, Integer page) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Book> bookPage = bookRepository.findAll(pageRequest);
            List<BookDTO> bookDTOList = new ArrayList<>();
            for (Book book : bookPage) {
                BookDTO bookDTO = BookMapping.toDto(book);
                bookToBookDto(bookDTO);
                bookDTOList.add(bookDTO);
            }
            Page<BookDTO> result = new PageImpl(bookDTOList, bookPage.getPageable(), bookPage.getTotalPages());
            return new ResponseDTO<>(true, 0, "OK", result, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO<>(false, -2, "Ma'lumot qidirishda xatolik mavjud", null, null);
        }
    }


    public ResponseDTO<BookDTO> getBook(Integer id) {
        try {
            Optional<Book> bookOptional = bookRepository.findById(id);
            if (bookOptional.isPresent()) {
                Book book = bookOptional.get();
                BookDTO bookDTO = BookMapping.toDto(book);
                return new ResponseDTO<>(true, 0, "OK", bookDTO, new HashMap<>());
            }
            return new ResponseDTO<>(false, -1, String.format("BookID = %d ma'lumot topilmadi", id), new BookDTO(id), null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO<>(false, -2, "Ma'lumot izlashda xatolik mavjud", new BookDTO(id), null);
        }
    }

    public ResponseDTO<BookDTO> addAndUpdate(BookDTO bookDTO) {
        Book book = BookMapping.toEntity(bookDTO);
        bookToBookDto(bookDTO);
        try {
            bookRepository.save(book);
            return new ResponseDTO<>(true, 0, "OK", bookDTO, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO<>(false, -3, "Ma'lumot saqlashda xatolik!", bookDTO, null);
        }
    }

    private void bookToBookDto(BookDTO bookDTO) {
        bookDTO.setAuthor(
                AuthorMapping.toDto(
                        authorRepository.getById(
                                bookDTO.getAuthor().getId()
                        )
                )
        );
        bookDTO.setPublisher(
                PublisherMapping.toDto(
                        publisherRepository.getById(
                                bookDTO.getPublisher().getId()
                        )
                )
        );
    }

    public ResponseDTO<BookDTO> addBook(BookDTO bookDTO) {
        Map<String, String> errors = bookValid.validPost(bookDTO);
        if (errors.size()>0) return new ResponseDTO<>(false, -1, "Validatsiyada xatolik", bookDTO, errors);
        bookDTO.setId(null);
        return addAndUpdate(bookDTO);
    }

    public ResponseDTO<BookDTO> updateBook(BookDTO bookDTO) {
        Map<String, String> errors = bookValid.validPut(bookDTO);
        if (errors.size()>0) return new ResponseDTO<>(false, -1, "Validatsiyada xatolik", bookDTO, errors);
        return addAndUpdate(bookDTO);
    }

    public ResponseDTO<BookDTO> deleteBook(BookDTO bookDTO) {
        try {
            Optional<Book> bookOptional = bookRepository.findById(bookDTO.getId());
            if (bookOptional.isPresent()) {
                Book book = bookOptional.get();
                try {
                    BookMapping.setDto(bookDTO, book);
                    bookToBookDto(bookDTO);
                    bookRepository.delete(book);
                    return new ResponseDTO<>(true, 0, "OK", bookDTO, null);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new ResponseDTO<>(false, -4, "Ma'lumot o'chirishda xatolik!", bookDTO, null);
                }
            }
            return new ResponseDTO<>(false, -1, String.format("BookId = %d bo'yicha ma'lumot topilmadi",bookDTO.getId()), bookDTO, null);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseDTO<>(false, -3, "Ma'lumot izlashda xatolik!", bookDTO, null);
        }
    }
}
