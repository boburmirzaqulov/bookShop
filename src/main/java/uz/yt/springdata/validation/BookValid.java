package uz.yt.springdata.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.yt.springdata.dto.BookDTO;
import uz.yt.springdata.repository.BookRepository;
import uz.yt.springdata.service.AuthorService;
import uz.yt.springdata.service.PublisherService;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class BookValid {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final PublisherService publisherService;

    public void validCost(BookDTO bookDTO, Map<String, String> errors) {
        if (bookDTO.getCost().intValue() < 0) {
            errors.put("cost", "Kitob narxi manfiy son bo'lishi mumkinmas");
        }
    }

    public void validPublishedDate(BookDTO bookDTO, Map<String, String> errors) {
        String publishedDate = bookDTO.getPublishedDate();
        if (!Pattern.matches("\\d{4}-\\d{2}-\\d{2}", publishedDate)) {
            errors.put("publishedDate", "Sana formati mos kelmadi. To'g'ri format YYYY-MM-DD");
            return;
        }
        String[] chapters = publishedDate.split("-");
        Integer[] dates = new Integer[chapters.length];
        for (int i = 0; i < chapters.length; i++) {
            dates[i] = Integer.parseInt(chapters[i]);
        }
        if (dates[1] > 12 || dates[1] == 0) {
            errors.put("publishedDate", "Sana formati mos kelmadi. To'g'ri format YYYY-MM-DD va 1<=MM<=12");
        }
        if (dates[2] > 31 || dates[2] == 0) {
            errors.put("publishedDate", "Sana formati mos kelmadi. To'g'ri format YYYY-MM-DD va 01<=DD<=31");
        }
    }

    public void validPageCount(BookDTO bookDTO, Map<String, String> errors) {
        if (bookDTO.getPageCount() < 0) {
            errors.put("pageCoount", "Kitob betlari soni manfiy bo'lishi mumkinmas");
        }
    }

    public void validAuthorId(BookDTO bookDTO, Map<String, String> errors) {
        Integer id = bookDTO.getAuthor().getId();
        if (id == null || !authorService.existsById(id)) {
            errors.put("authorId", String.format("Berilgan authorID = %d mavjud emas", id));
        }
    }

    public void validPublisherId(BookDTO bookDTO, Map<String, String> errors) {
        Integer id = bookDTO.getPublisher().getId();
        if (id == null || !publisherService.existsById(id)) {
            errors.put("publisherId", String.format("Berilgan publisherID = %d mavjud emas", id));
        }
    }

    public void validBookId(BookDTO bookDTO, Map<String, String> errors) {
        Integer id = bookDTO.getId();
        if (id == null || !bookRepository.existsById(id)) {
            errors.put("bookId", String.format("Berilgan bookID = %d mavjud emas", id));
        }
    }

    public void validBookName(BookDTO bookDTO, Map<String, String> errors) {
        String bookName = bookDTO.getName();
        if (bookName != null) {
            if (bookName.isEmpty()) {
                errors.put("bookName", "Kitobning nomi mavjud emas");
            }
        }
        errors.put("bookName", "Kitobning nomi mavjud emas");
    }

    public Map<String, String> validPost(BookDTO bookDTO) {
        Map<String, String> errors = new HashMap<>();
        validPostAndPut(bookDTO, errors);
        return errors;
    }

    public Map<String, String> validPut(BookDTO bookDTO) {
        Map<String, String> errors = new HashMap<>();
        validBookId(bookDTO, errors);
        if (errors.size()>0) return errors;
        validPostAndPut(bookDTO, errors);
        return errors;
    }

    private void validPostAndPut(BookDTO bookDTO, Map<String, String> errors) {
        validCost(bookDTO, errors);
        validBookName(bookDTO, errors);
        validPublishedDate(bookDTO, errors);
        validPageCount(bookDTO, errors);
        validAuthorId(bookDTO, errors);
        validPublisherId(bookDTO, errors);
    }
}
