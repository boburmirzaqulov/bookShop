package uz.yt.springdata.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.yt.springdata.dto.ResponseDTO;
import uz.yt.springdata.dto.StudentDTO;
import uz.yt.springdata.service.StudentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("student")
public class StudentResource {
    private final StudentService studentService;

    @GetMapping("/get-by-id")
    public ResponseDTO<StudentDTO> getById(@RequestParam Integer id){
        return studentService.getById(id);
    }

    @PostMapping("/add")
    public ResponseDTO<StudentDTO> addStudent(@RequestBody StudentDTO studentDTO){
        return studentService.addStudent(studentDTO);
    }

    @PostMapping("/add-course")
    public ResponseDTO<StudentDTO> addCourse(@RequestBody StudentDTO studentDTO){
        return studentService.addCourse(studentDTO);
    }
}
