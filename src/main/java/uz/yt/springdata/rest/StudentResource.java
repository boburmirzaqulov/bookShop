package uz.yt.springdata.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.yt.springdata.dto.ResponseDTO;
import uz.yt.springdata.dto.StudentDTO;
import uz.yt.springdata.service.StudentService;

@RestController
@RequiredArgsConstructor
public class StudentResource {
    private final StudentService studentService;

    @GetMapping("/student")
    public ResponseDTO<StudentDTO> getById(@RequestParam Integer id){
        return studentService.getById(id);
    }
}
