package uz.yt.springdata.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.yt.springdata.dao.Author;
import uz.yt.springdata.dao.Book;
import uz.yt.springdata.dao.Publisher;
import uz.yt.springdata.dto.AuthorDTO;
import uz.yt.springdata.dto.BookDTO;
import uz.yt.springdata.dto.PublisherDTO;
import uz.yt.springdata.dto.ResponseDTO;
import uz.yt.springdata.mapping.AuthorMapping;
import uz.yt.springdata.mapping.BookMapping;
import uz.yt.springdata.mapping.PublisherMapping;
import uz.yt.springdata.repository.AuthorRepository;
import uz.yt.springdata.repository.BookRepository;
import uz.yt.springdata.repository.PublisherRepository;
import uz.yt.springdata.validation.BookValid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                ResponseDTO<BookDTO> responseDTO = bookValid.validAuthorIdAndPublisherId(bookDTO);
                if (!responseDTO.isSuccess()) return new ResponseDTO<>(false, -7, "Validatsiyada xatolik", null);
                try {
                    Optional<Author> optionalAuthor = authorRepository.findById(book.getAuthorId());
                    bookDTO.setAuthorDTO(AuthorMapping.toDto(optionalAuthor.get()));
                    try {
                        Optional<Publisher> optionalPublisher = publisherRepository.findById(book.getPublisherId());
                        bookDTO.setPublisherDTO(PublisherMapping.toDto(optionalPublisher.get()));
                        bookDTOList.add(bookDTO);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return new ResponseDTO<>(false, -5, "Ma'lumot izlashda xatolik!", null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return new ResponseDTO<>(false, -5, "Ma'lumot izlashda xatolik!", null);
                }
            }

            Page<BookDTO> result = new PageImpl(bookDTOList, bookPage.getPageable(), bookPage.getTotalPages());
            return new ResponseDTO<>(true, 0, "OK", result);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO<>(false, -1, "ERROR", null);
        }
    }


    public ResponseDTO<BookDTO> getBook(Integer id) {
        try {
            Optional<Book> bookOptional = bookRepository.findById(id);
            if (!bookOptional.isPresent()) {
                return new ResponseDTO<>(false, -7, String.format("Berilgan bookID = %d mavjud emas", id), null);
            }
            Book book = bookOptional.get();
            BookDTO bookDTO = BookMapping.toDto(book);

            ResponseDTO<BookDTO> responseDTO = bookValid.validAuthorIdAndPublisherId(bookDTO);
            if (!responseDTO.isSuccess()) return responseDTO;

            try {
                Optional<Author> authorOptional = authorRepository.findById(book.getAuthorId());
                bookDTO.setAuthorDTO(AuthorMapping.toDto(authorOptional.get()));
                try {
                    Optional<Publisher> publisherOptional = publisherRepository.findById(book.getPublisherId());
                    bookDTO.setPublisherDTO(PublisherMapping.toDto(publisherOptional.get()));
                    return new ResponseDTO<>(true, 0, "OK", bookDTO);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new ResponseDTO<>(false, -5, "Ma'lumot izlashda xatolik!", null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseDTO<>(false, -5, "Ma'lumot izlashda xatolik!", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO<>(false, -1, "ERROR", null);
        }
    }

    public ResponseDTO<BookDTO> addBook(BookDTO bookDTO, Integer id) {
        ResponseDTO<BookDTO> responseDTO = bookValid.validAll(bookDTO, id);
        if (!responseDTO.isSuccess()) return responseDTO;

        try {
            Book book = BookMapping.toEntity(bookDTO);
            book.setId(id);
            bookDTO.setAuthorDTO(
                    AuthorMapping.toDto(
                            authorRepository.getById(
                                    book.getAuthorId()
                            )
                    )
            );
            bookDTO.setPublisherDTO(
                    PublisherMapping.toDto(
                            publisherRepository.getById(
                                    book.getPublisherId()
                            )
                    )
            );
            try {
                bookRepository.save(book);
                BookMapping.setDto(bookDTO, book);
                return new ResponseDTO<>(true, 0, "OK", bookDTO);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseDTO<>(false, -3, "Ma'lumot saqlashda xatolik!", bookDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO<>(false, -2, "Ma'lumot qidirishda xatolik mavjud", bookDTO);
        }
    }

    public ResponseDTO<BookDTO> updateBook(BookDTO bookDTO) {
        return addBook(bookDTO, bookDTO.getId());
    }

    public ResponseDTO<BookDTO> deleteBook(BookDTO bookDTO) {
        ResponseDTO<BookDTO> responseDTO = bookValid.validBookId(bookDTO);
        if (responseDTO != null) {
            return responseDTO;
        }
        Optional<Book> bookOptional = bookRepository.findById(bookDTO.getId());
        Book book = bookOptional.get();
        try {
            BookMapping.setDto(bookDTO, book);
            bookDTO.setAuthorDTO(new AuthorDTO());
            bookDTO.getAuthorDTO().setId(book.getAuthorId());
            bookDTO.setPublisherDTO(new PublisherDTO());
            bookDTO.getPublisherDTO().setId(book.getPublisherId());
            bookRepository.delete(book);
            return new ResponseDTO<>(true, 0, "OK", bookDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO<>(false, -4, "Ma'lumot o'chirishda xatolik!", bookDTO);
        }
    }
}
