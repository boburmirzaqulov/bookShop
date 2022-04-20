package uz.yt.springdata.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.yt.springdata.dto.BookDTO;
import uz.yt.springdata.dto.ResponseDTO;
import uz.yt.springdata.service.BookService;

@RestController
@RequiredArgsConstructor
public class BookResource {
    private final BookService bookService;

    @GetMapping("/books")
    public ResponseDTO<Page<BookDTO>> getAllBooks(@RequestParam Integer size, @RequestParam Integer page){
        return bookService.getAllBooks(size, page);
    }

    @GetMapping("/book/{id}")
    public ResponseDTO<BookDTO> getBook(@PathVariable Integer id){
        return bookService.getBook(id);
    }

    @PostMapping("/book")
    public ResponseDTO<BookDTO> addBook(@RequestBody BookDTO bookDTO){
        return bookService.addBook(bookDTO);
    }

    @PutMapping("/book")
    public ResponseDTO<BookDTO> updateBook(@RequestBody BookDTO bookDTO){
        return bookService.updateBook(bookDTO);
    }

    @DeleteMapping("/book")
    public ResponseDTO<BookDTO> deleteBook(@RequestBody BookDTO bookDTO){
        return bookService.deleteBook(bookDTO);
    }
}
