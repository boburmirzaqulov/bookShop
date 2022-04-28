package uz.yt.springdata.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.yt.springdata.dto.CourseDTO;
import uz.yt.springdata.dto.ResponseDTO;
import uz.yt.springdata.service.CourseService;

@RestController
@RequiredArgsConstructor
@RequestMapping("course")
public class CourseResource {
    private final CourseService courseService;

    @GetMapping("/{id}")
    public ResponseDTO<CourseDTO> getById(@PathVariable Integer id){
        return courseService.getById(id);
    }

    @PostMapping("/add-student")
    public ResponseDTO<CourseDTO> addStudent(@RequestBody CourseDTO courseDTO){
        return courseService.addStudent(courseDTO);
    }
    @PostMapping("/add-course")
    public ResponseDTO<CourseDTO> addCourse(@RequestBody CourseDTO courseDTO){
        return courseService.addCourse(courseDTO);
    }
}
