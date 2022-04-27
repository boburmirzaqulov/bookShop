package uz.yt.springdata.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import uz.yt.springdata.dto.CourseDTO;
import uz.yt.springdata.dto.ResponseDTO;
import uz.yt.springdata.service.CourseService;

@RestController
@RequiredArgsConstructor
public class CourseResource {
    private final CourseService courseService;

    @GetMapping("/course/{id}")
    public ResponseDTO<CourseDTO> getById(@PathVariable Integer id){
        return courseService.getById(id);
    }

}
