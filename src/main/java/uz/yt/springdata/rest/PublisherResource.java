package uz.yt.springdata.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.yt.springdata.dto.PublisherDTO;
import uz.yt.springdata.dto.ResponseDTO;
import uz.yt.springdata.service.PublisherService;

@RestController
@RequiredArgsConstructor
public class PublisherResource {
    private final PublisherService publisherService;

    @GetMapping("/publishers")
    public ResponseDTO<Page<PublisherDTO>> getAllPublishers(@RequestParam Integer size, @RequestParam Integer page){
        return publisherService.getAllPublishers(size, page);
    }
}
