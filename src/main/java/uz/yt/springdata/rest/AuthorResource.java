package uz.yt.springdata.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.yt.springdata.dto.AuthorDTO;
import uz.yt.springdata.dto.ResponseDTO;
import uz.yt.springdata.service.AuthorService;

@RestController
@RequiredArgsConstructor
public class AuthorResource {
    private final AuthorService authorService;

    @GetMapping("/authors")
    public ResponseDTO<Page<AuthorDTO>> getAllAuthors(@RequestParam Integer size, @RequestParam Integer page){
        return authorService.getAllAuthors(size, page);
    }
}
